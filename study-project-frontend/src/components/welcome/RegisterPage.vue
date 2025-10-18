<script setup>
import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive, ref} from "vue";
import {post} from "@/net/index.js";

const formRef = ref()
const isEmailValid = ref(false)
const coldTime = ref(0)

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: '',
})

const validateUsername = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入用户名'))
  } else if (!/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/.test(value)) {
    callback(new Error('用户名不能包含特殊字符，只能是中文/英文'))
  } else {
    callback()
  }
}

const validatePassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== form.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}


const onValidate = (prop, isValid) => {
  if (prop === 'email')
    isEmailValid.value = isValid
}

const rules = {
  username: [
    {validator: validateUsername, trigger: ['blur', 'change']},
    {min: 2, max: 8, message: '用户名的长度必须在2-8个字符之间', trigger: ['blur', 'change']},
  ],
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


const register = () => {
  formRef.value.validate((isValid) => {
    if (isValid) {
      post("api/auth/register", {
        username: form.username,
        password: form.password,
        email: form.email,
        code: form.code
      }, (message) => {
        ElMessage.success(message)
        router.push('/')
      })
    } else {
      ElMessage.warning('请完整填写注册表单内容!')
    }
  })
}

const validateEmail = () => {
  post('api/auth/valid-email', {
    email: form.email
  }, (message) => {
    ElMessage.success(message)
    coldTime.value = 60
    setInterval(() => coldTime.value--, 1000)
  })
}

</script>

<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="text-align: center;margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">注册新用户</div>
      <div style="font-size: 14px;color: grey">欢迎注册我们的学习平台，请在下面填写相关信息</div>
    </div>
    <div style="margin-top: 50px">
      <el-form :model="form" :rules="rules" @validate="onValidate" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="form.username" :maxlength="8" type="text" placeholder="用户名">
            <template #prefix>
              <el-icon>
                <User/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
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
        <el-form-item prop="email">
          <el-input v-model="form.email" type="text" placeholder="电子邮箱地址">
            <template #prefix>
              <el-icon>
                <Message/>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="code">
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
    <div style="margin-top: 80px">
      <el-button style="width: 270px" type="warning" plain @click="register()">
        立即注册
      </el-button>
    </div>

    <el-divider>
      <span style="color: grey;font-size: 14px">已有账户</span>
    </el-divider>
    <div style="margin-top: 10px">
      <el-button style="width: 270px" type="primary" plain @click="router.push('/')">
        返回登录
      </el-button>
    </div>
  </div>


</template>

<style scoped>

</style>