<script setup>
import {reactive, ref} from "vue";
import {EditPen, Lock, Message} from "@element-plus/icons-vue";

const active = ref(0)

const form = reactive({
  email: '',
  code: '',
})

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 16, message: '密码的长度必须在6-16个字符之间', trigger: ['blur', 'change']},
  ],
  password_repeat: [
    {validator: validatePassword, trigger: ['blur', 'change']}
  ],
  email: [
    {required: true, message: '请输入电子邮件', trigger: ['blur']},
    {
      type: 'email',
      message: '请输入合法的电子邮件地址', trigger: ['blur', 'change']
    }
  ],
  code: [
    {required: true, message: '请输入验证码', trigger: ['blur']}
  ]
}
</script>

<template>
  <div style="margin: 30px 20px;width: 100%">
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="验证电子邮件"/>
      <el-step title="重新设定密码"/>
    </el-steps>
  </div>
  <transition name="el-fade-in-linear" mode="out-in">
    <div style="text-align:center;margin: 0 20px;height: 100%" v-if="active === 0">
      <div style="text-align: center;margin-top: 80px">
        <div style="font-size: 25px;font-weight: bold">重置密码</div>
        <div style="font-size: 14px;color: grey">请输入需要重置密码的邮件地址</div>
      </div>
      <div style="margin-top: 50px">
        <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
          <el-form-item prop="email">
            <el-input v-model="form.email" type="email" placeholder="电子邮箱地址">
              <template #prefix>
                <el-icon>
                  <Message/>
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="code" style="width: 100%">
            <el-row :gutter="10">
              <el-col :span="18">
                <el-input v-model="form.code" :maxlength="6" type="text" placeholder="请输入验证码">
                  <template #prefix>
                    <el-icon>
                      <EditPen/>
                    </el-icon>
                  </template>
                </el-input>
              </el-col>
              <el-col :span="6">
                <el-button type="success" plain :disabled="!isEmailValid || coldTime > 0" @click="validateEmail()">
                  {{ coldTime > 0 ? '请稍后' + coldTime + '秒' : '获取验证码' }}
                </el-button>
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-top: 70px">
        <el-button @click="active=1" style="width: 270px; " type="danger" plain>验证</el-button>
      </div>
    </div>
  </transition>
  <transition name="el-fade-in-linear" mode="out-in">
    <div style="text-align:center;margin: 0 20px;height: 100%" v-if="active === 1">
      <div style="text-align: center;margin-top: 80px">
        <div style="font-size: 25px;font-weight: bold">重置密码</div>
        <div style="font-size: 14px;color: grey">请输入您的新密码，务必牢记，避免丢失</div>
      </div>
      <div style="margin-top: 50px">
        <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
          <el-form-item prop="password">
            <el-input v-model="form.password" :maxlength="16" type="password" placeholder="密码">
              <template #prefix>
                <el-icon>
                  <Lock/>
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item prop="password_repeat">
            <el-input v-model="form.password_repeat" :maxlength="16" type="password" placeholder="重复密码">
              <template #prefix>
                <el-icon>
                  <Lock/>
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
        </el-form>
      </div>
      <div style="margin-top: 70px">
        <el-button @click="" style="width: 270px; " type="danger" plain>重置密码</el-button>
      </div>
    </div>
  </transition>
</template>
<style scoped>

</style>