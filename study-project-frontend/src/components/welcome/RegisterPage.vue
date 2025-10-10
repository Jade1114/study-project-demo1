<script setup>
import {EditPen, Lock, Message, User} from "@element-plus/icons-vue";
import router from "@/router";
import {reactive} from "vue";

const form = reactive({
  username: '',
  password: '',
  password_repeat: '',
  email: '',
  code: '',
})

</script>

<template>
  <div style="text-align: center; margin: 0 20px">
    <div style="text-align: center;margin-top: 100px">
      <div style="font-size: 25px;font-weight: bold">注册新用户</div>
      <div style="font-size: 14px;color: grey">欢迎注册我们的学习平台，请在下面填写相关信息</div>
    </div>
    <div style="margin-top: 50px">
      <el-input v-model="form.username" type="text" placeholder="用户名">
        <template #prefix>
          <el-icon>
            <User/>
          </el-icon>
        </template>
      </el-input>
      <el-input v-model="form.password" type="password" placeholder="密码" style="margin-top: 10px">
        <template #prefix>
          <el-icon>
            <Lock/>
          </el-icon>
        </template>
      </el-input>
      <el-input v-model="form.password_repeat" type="password" placeholder="重复密码" style="margin-top: 10px">
        <template #prefix>
          <el-icon>
            <Lock/>
          </el-icon>
        </template>
      </el-input>
      <el-input v-model="form.email" type="email" placeholder="电子邮箱地址" style="margin-top: 10px">
        <template #prefix>
          <el-icon>
            <Message/>
          </el-icon>
        </template>
      </el-input>
    </div>

    <div style="margin-top: 10px">
      <el-row :gutter="10">
        <el-col :span="18">
          <el-input v-model="form.code" type="text" placeholder="请输入验证码">
            <template #prefix>
              <el-icon>
                <EditPen/>
              </el-icon>
            </template>
          </el-input>
        </el-col>
        <el-col :span="6">
          <el-button type="success" plain>
            获取验证码
          </el-button>
        </el-col>
      </el-row>
    </div>

    <div style="margin-top: 80px">
      <el-button style="width: 270px" type="warning" plain>
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