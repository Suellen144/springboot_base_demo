package com.example.demo.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.common.HttpMessage;
import com.example.demo.common.HttpStatus;
import com.example.demo.common.R;
import com.example.demo.entity.SysUser;
import com.example.demo.mapper.SysUserMapper;
import com.example.demo.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zhk
 * @since 2021-12-21
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    RedisUtils redisUtils;

    /**
     *用于生成短信验证码存到redis时作为key的标识符*/
    private final String MESSAGE_CODE = "messageCode_";
    /**
     *短信验证码生成次数统计*/
    private final String MESSAGE_SUM = "sum_";
    /**
     *短信验证码生成次数限制次数*/
    private final int LIMIT_COUNT = 3;
    /**
     *登录限制次数*/
    private final int count = 5;
    /**
     *登录统计次数*/
    int sum = 0;

    /**
     * 用户注册
     * @param sysUser 用户对象
     */
    @Override
    public R register(SysUser sysUser) {
        if(Objects.isNull(sysUser)) {
            return R.error(HttpStatus.ERROR,"用户对象不能为空");
        }
        if(StringUtils.isBlank(sysUser.getAccount())) {
            return R.error(HttpStatus.ERROR,"用户账号不能为空");
        }
        if(StringUtils.isBlank(sysUser.getName())) {
            sysUser.setName(sysUser.getAccount());
        }
        if(StringUtils.isBlank(sysUser.getPassword())) {
            return R.error(HttpStatus.ERROR,"用户密码不能为空");
        }
        if(!RegularCheckUtil.checkPassWord(sysUser.getPassword())){
            return R.error(HttpStatus.ERROR,"口令长度大于等于8位且为数字、字母、特殊字符2种及以上组合的");
        }

       /* try {
            //解密密码
            String password = RsaUtil.decrypt(sysUser.getPassword());
            sysUser.setPassword(password);
        }catch (Exception e) {
            e.printStackTrace();
            //登录失败
            return R.error(HttpStatus.ERROR,"密码解密失败");
        }*/

        if(StringUtils.isBlank(sysUser.getEmail())) {
            return R.error(HttpStatus.ERROR,"用户邮箱不能为空");
        }
        if(!RegularCheckUtil.checkEmail(sysUser.getEmail())){
            return R.error(HttpStatus.ERROR,"邮箱格式错误");
        }
        if(StringUtils.isBlank(sysUser.getPhone())) {
            return R.error(HttpStatus.ERROR,"手机号码不能为空");
        }
        if(!RegularCheckUtil.checkPhone(sysUser.getPhone())){
            return R.error(HttpStatus.ERROR,"手机号码格式错误");
        }
        if(sysUser.getType() == null) {
            return R.error(HttpStatus.ERROR,"无法识别要注册的用户类型");
        }
        if(sysUser.getGender() == null) {
            return R.error(HttpStatus.ERROR,"用户性别不能为空");
        }
        if(StringUtils.isBlank(sysUser.getMessageAuthCode())) {
            return R.error(HttpStatus.ERROR,"请输入短信验证码");
        }
        if(!verifyMessageCode(sysUser.getPhone(),sysUser.getMessageAuthCode())) {
            return R.error(HttpStatus.ERROR,"短信验证码校验失败");
        }

        QueryWrapper query = new QueryWrapper();
        query.eq("account",sysUser.getAccount());
        if(Objects.nonNull(sysUserMapper.selectOne(query))){
            return R.error(HttpStatus.ERROR,"该账号已经被注册");
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("phone",sysUser.getPhone());
        if(Objects.nonNull(sysUserMapper.selectOne(queryWrapper))){
            return R.error(HttpStatus.ERROR,"该手机号已经被注册");
        }

        sysUser.setPassword(EncryptionUtil.encryption(sysUser.getPassword()));
        sysUser.setLastUpdatePasswordTime(new Date());

        sysUserMapper.insert(sysUser);

        return R.ok();
    }

    /**
     * 用户登录
     * @param sysUser 用户对象
     */
    @Override
    public R login(SysUser sysUser) {
        if(Objects.isNull(sysUser)) {
            return R.error(HttpStatus.ERROR,"用户对象不能为空");
        }
        if(StringUtils.isBlank(sysUser.getAccount())) {
            return R.error(HttpStatus.ERROR,"用户账号不能为空");
        }
        if(StringUtils.isBlank(sysUser.getPassword())) {
            return R.error(HttpStatus.ERROR,"用户密码不能为空");
        }

        sum = redisUtils.hasKey(sysUser.getAccount())?(int)redisUtils.get(sysUser.getAccount()):0;
        if(sum >= count){
            return R.error(HttpStatus.ERROR,"登录失败次数过来，请稍后再试");
        }

        SysUser user = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("account",sysUser.getAccount()));
        if(Objects.isNull(user)){
            if(redisUtils.hasKey(sysUser.getAccount())){
                redisUtils.incr(sysUser.getAccount(),1);
            }else {
                redisUtils.set(sysUser.getAccount(),sum,30);
            }
            return R.error(HttpStatus.ERROR,"账号密码错误,请重新输入");
        }

        String encryption = EncryptionUtil.encryption(sysUser.getPassword());
        if(!encryption.equals(user.getPassword())){
            redisUtils.incr(sysUser.getAccount(),1);
            return R.error(HttpStatus.ERROR,"账号密码错误,请重新输入");
        }

        user.setPassword(null);
        String token = JwtUtil.generateJwt(user);
        user.setToken(token);
        redisUtils.del(sysUser.getAccount());
        redisUtils.set(user.getUserId(),token,900);
        return R.ok(HttpStatus.SUCCESS, "登录成功",user);
    }

    /**
     * 生成六位数短信验证码
     * @param phone 手机号码
     */
    @Override
    public R getMessageCode(String phone) {
        if(StringUtils.isBlank(phone)) {
            return R.error(HttpStatus.ERROR,"手机号码不能为空");
        }
        if(!RegularCheckUtil.checkPhone(phone)){
            return R.error(HttpStatus.ERROR,"手机号码格式错误");
        }
        try {
            boolean hasKey = redisUtils.hasKey(MESSAGE_CODE + phone);
            if(!hasKey){
                String messageAuthCode = MessageCodeUtil.getMessageAuthCode();
                redisUtils.set(MESSAGE_CODE + phone,messageAuthCode,1800);
                redisUtils.set(MESSAGE_SUM + phone,1,900);
                return R.ok(HttpStatus.SUCCESS,HttpMessage.SUCCESS,messageAuthCode);
            }

            String messageAuthCode = redisUtils.get(MESSAGE_CODE + phone).toString();
            redisUtils.incr(MESSAGE_SUM + phone,1);
            int sum = (int)redisUtils.get(MESSAGE_SUM + phone);
            if(sum > LIMIT_COUNT) {
                return R.error(HttpStatus.ERROR,"已生成短信验证码并且15分钟内重发次数超过了"+LIMIT_COUNT+"次,请15分钟后再试");
            }

            return R.ok(HttpStatus.SUCCESS,"已生成短信验证码并且第"+sum+"次发送",messageAuthCode);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(HttpStatus.ERROR,HttpMessage.ERROR);
        }
    }

    /**
     * 校验手机号和短信验证码
     * @param phone 手机号码
     * @param messageCode 短信验证码
     */
    @Override
    public R verifyPhoneAndMessageCode(String phone, String messageCode) {
        if(StringUtils.isBlank(phone)) {
            return R.error(HttpStatus.ERROR,"手机号码不能为空");
        }
        if(!RegularCheckUtil.checkPhone(phone)){
            return R.error(HttpStatus.ERROR,"手机号码格式错误");
        }
        if(StringUtils.isBlank(messageCode)) {
            return R.error(HttpStatus.ERROR,"请输入短信验证码");
        }
        if(!verifyMessageCode(phone,messageCode)) {
            return R.error(HttpStatus.ERROR,"短信验证码校验失败");
        }
        return R.ok(HttpStatus.SUCCESS,"校验成功");
    }

    /**
     * 重置密码
     * @param phone 手机号码
     * @param newPassword 新密码
     */
    @Override
    public R resetPassword(String phone, String newPassword) {
        if(StringUtils.isBlank(phone)) {
            return R.error(HttpStatus.ERROR,"手机号码不能为空");
        }
        if(!RegularCheckUtil.checkPhone(phone)){
            return R.error(HttpStatus.ERROR,"手机号码格式错误");
        }
        if(StringUtils.isBlank(newPassword)){
            return R.error(HttpStatus.ERROR,"新密码不能为空");
        }
        if(!RegularCheckUtil.checkPassWord(newPassword)){
            return R.error(HttpStatus.ERROR,"口令长度大于等于8位且为数字、字母、特殊字符2种及以上组合的");
        }

        SysUser sysUser = new SysUser();
        sysUser.setPassword(EncryptionUtil.encryption(newPassword));
        sysUser.setLastUpdatePasswordTime(new Date());
        int update = sysUserMapper.update(sysUser, new QueryWrapper<SysUser>().eq("phone", phone));
        if(update<=0){
            return R.error(HttpStatus.ERROR,"找不到匹配的数据，请检查手机号是否输入正确");
        }
        return R.ok(HttpStatus.SUCCESS,"修改成功");
    }

    /**
     * 修改密码
     * @param userId 用户id
     * @param newPassword 新密码
     * @param oldPassword 旧密码
     */
    @Override
    public R updatePassword(String userId, String newPassword, String oldPassword) {
        if(StringUtils.isBlank(userId)) {
            return R.error(HttpStatus.ERROR,"用户id不能为空");
        }
        if(StringUtils.isBlank(oldPassword)){
            return R.error(HttpStatus.ERROR,"旧密码不能为空");
        }
        if(StringUtils.isBlank(newPassword)){
            return R.error(HttpStatus.ERROR,"新密码不能为空");
        }
        if(!RegularCheckUtil.checkPassWord(newPassword)){
            return R.error(HttpStatus.ERROR,"口令长度大于等于8位且为数字、字母、特殊字符2种及以上组合的");
        }

        SysUser sysUser = new SysUser();
        sysUser.setPassword(EncryptionUtil.encryption(newPassword));
        sysUser.setLastUpdatePasswordTime(new Date());
        int update = sysUserMapper.update(sysUser, new QueryWrapper<SysUser>()
                .eq("user_id", userId).eq("password",EncryptionUtil.encryption(oldPassword)));
        if(update<=0){
            return R.error(HttpStatus.ERROR,"找不到匹配的数据，请检查手机号和旧密码是否输入正确");
        }
        return R.ok(HttpStatus.SUCCESS,"修改成功");
    }


    /**
     * 修改手机号码
     * @param userId 用户id
     * @param newPhone 新密码
     * @param oldPhone 旧密码
     */
    @Override
    public R updatePhone(String userId, String newPhone, String oldPhone) {
        if(StringUtils.isBlank(userId)) {
            return R.error(HttpStatus.ERROR,"用户id不能为空");
        }
        if(!RegularCheckUtil.checkPhone(newPhone)){
            return R.error(HttpStatus.ERROR,"新手机号不能为空");
        }
        if(!RegularCheckUtil.checkPhone(oldPhone)){
            return R.error(HttpStatus.ERROR,"旧手机号不能为空");
        }

        SysUser sysUser = new SysUser();
        sysUser.setPhone(newPhone);
        int update = sysUserMapper.update(sysUser, new QueryWrapper<SysUser>()
                .eq("user_id", userId).eq("phone",oldPhone));
        if(update<=0){
            return R.error(HttpStatus.ERROR,"找不到匹配的数据，请检查手机号是否输入正确");
        }
        return R.ok(HttpStatus.SUCCESS,"修改成功");
    }

    /**
     * 查询所有用户
     */
    @Override
    public R findUserAll() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        if(Objects.isNull(sysUsers) || sysUsers.size() <=0){
            return R.ok(HttpStatus.SUCCESS,"无用户数据");
        }
        return R.ok(HttpStatus.SUCCESS,HttpMessage.SUCCESS,sysUsers);
    }

    /**
     *校验六位数短信*
     *@param phone
     *@param messageAuthCode
     *@return
     */
    public boolean verifyMessageCode(String phone,String messageAuthCode){
        if(redisUtils.hasKey(MESSAGE_CODE + phone)){
            String code = Objects.nonNull(redisUtils.get(MESSAGE_CODE + phone))?
                    redisUtils.get(MESSAGE_CODE + phone).toString():null;
            if(StringUtils.isNotBlank(code) && messageAuthCode.equals(code)){
                redisUtils.del(MESSAGE_CODE + phone);
                redisUtils.del(MESSAGE_SUM + phone);
                return true;
            }
        }
        return false;
    }
}
