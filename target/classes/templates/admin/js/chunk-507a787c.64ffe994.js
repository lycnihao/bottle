(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-507a787c"],{"7cb0":function(e,t,a){},ac2a:function(e,t,a){"use strict";a.r(t);var s=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"main"},[a("a-form",{ref:"formLogin",staticClass:"user-layout-login",attrs:{id:"formLogin",form:e.form},on:{submit:e.handleSubmit}},[a("a-tabs",{attrs:{activeKey:e.customActiveKey,tabBarStyle:{textAlign:"center",borderBottom:"unset"}},on:{change:e.handleTabClick}},[a("a-tab-pane",{key:"tab1",attrs:{tab:"账号密码登录"}},[e.isLoginError?a("a-alert",{staticStyle:{"margin-bottom":"24px"},attrs:{type:"error",showIcon:"",message:"账户或密码错误"}}):e._e(),a("a-form-item",[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["username",{rules:[{required:!0,message:"请输入帐户名或邮箱地址"},{validator:e.handleUsernameOrEmail}],validateTrigger:"change"}],expression:"[\n              'username',\n              {rules: [{ required: true, message: '请输入帐户名或邮箱地址' }, { validator: handleUsernameOrEmail }], validateTrigger: 'change'}\n            ]"}],attrs:{size:"large",type:"text",placeholder:"用户名或邮箱"}},[a("a-icon",{style:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"user"},slot:"prefix"})],1)],1),a("a-form-item",[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["password",{rules:[{required:!0,message:"请输入密码"}],validateTrigger:"blur"}],expression:"[\n              'password',\n              {rules: [{ required: true, message: '请输入密码' }], validateTrigger: 'blur'}\n            ]"}],attrs:{size:"large",type:"password",autocomplete:"false",placeholder:"密码"}},[a("a-icon",{style:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"lock"},slot:"prefix"})],1)],1)],1),a("a-tab-pane",{key:"tab2",attrs:{tab:"手机号登录"}},[a("a-form-item",[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["mobile",{rules:[{required:!0,pattern:/^1[34578]\d{9}$/,message:"请输入正确的手机号"}],validateTrigger:"change"}],expression:"['mobile', {rules: [{ required: true, pattern: /^1[34578]\\d{9}$/, message: '请输入正确的手机号' }], validateTrigger: 'change'}]"}],attrs:{size:"large",type:"text",placeholder:"手机号"}},[a("a-icon",{style:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"mobile"},slot:"prefix"})],1)],1),a("a-row",{attrs:{gutter:16}},[a("a-col",{staticClass:"gutter-row",attrs:{span:16}},[a("a-form-item",[a("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["captcha",{rules:[{required:!0,message:"请输入验证码"}],validateTrigger:"blur"}],expression:"['captcha', {rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur'}]"}],attrs:{size:"large",type:"text",placeholder:"验证码"}},[a("a-icon",{style:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"mail"},slot:"prefix"})],1)],1)],1),a("a-col",{staticClass:"gutter-row",attrs:{span:8}},[a("a-button",{staticClass:"getCaptcha",attrs:{tabindex:"-1",disabled:e.state.smsSendBtn},domProps:{textContent:e._s(e.state.smsSendBtn?e.state.time+" s":"获取验证码")},on:{click:function(t){return t.stopPropagation(),t.preventDefault(),e.getCaptcha(t)}}})],1)],1)],1)],1),a("a-form-item",[a("a-checkbox",{directives:[{name:"decorator",rawName:"v-decorator",value:["rememberMe"],expression:"['rememberMe']"}]},[e._v("自动登录")]),a("router-link",{staticClass:"forge-password",staticStyle:{float:"right"},attrs:{to:{name:"recover",params:{user:"aaa"}}}},[e._v("忘记密码")])],1),a("a-form-item",{staticStyle:{"margin-top":"24px"}},[a("a-button",{staticClass:"login-button",attrs:{size:"large",type:"primary",htmlType:"submit",loading:e.state.loginBtn,disabled:e.state.loginBtn}},[e._v("确定")])],1),a("div",{staticClass:"user-login-other"},[a("span",[e._v("其他登录方式")]),a("a",[a("a-icon",{staticClass:"item-icon",attrs:{type:"alipay-circle"}})],1),a("a",[a("a-icon",{staticClass:"item-icon",attrs:{type:"taobao-circle"}})],1),a("a",[a("a-icon",{staticClass:"item-icon",attrs:{type:"weibo-circle"}})],1),a("router-link",{staticClass:"register",attrs:{to:{name:"register"}}},[e._v("注册账户")])],1)],1),e.requiredTwoStepCaptcha?a("two-step-captcha",{attrs:{visible:e.stepCaptchaVisible},on:{success:e.stepCaptchaSuccess,cancel:e.stepCaptchaCancel}}):e._e()],1)},i=[],r=(a("d3b7"),a("5530")),n=function(){var e=this,t=this,a=t.$createElement,s=t._self._c||a;return s("a-modal",{attrs:{centered:"",maskClosable:!1},on:{cancel:t.handleCancel},model:{value:t.visible,callback:function(e){t.visible=e},expression:"visible"}},[s("div",{style:{textAlign:"center"},attrs:{slot:"title"},slot:"title"},[t._v("两步验证")]),s("template",{slot:"footer"},[s("div",{style:{textAlign:"center"}},[s("a-button",{key:"back",on:{click:t.handleCancel}},[t._v("返回")]),s("a-button",{key:"submit",attrs:{type:"primary",loading:t.stepLoading},on:{click:t.handleStepOk}},[t._v(" 继续 ")])],1)]),s("a-spin",{attrs:{spinning:t.stepLoading}},[s("a-form",{attrs:{layout:"vertical","auto-form-create":function(t){e.form=t}}},[s("div",{staticClass:"step-form-wrapper"},[t.stepLoading?s("p",{staticStyle:{"text-align":"center"}},[t._v("正在验证.."),s("br"),t._v("请稍后")]):s("p",{staticStyle:{"text-align":"center"}},[t._v("请在手机中打开 Google Authenticator 或两步验证 APP"),s("br"),t._v("输入 6 位动态码")]),s("a-form-item",{style:{textAlign:"center"},attrs:{hasFeedback:"",fieldDecoratorId:"stepCode",fieldDecoratorOptions:{rules:[{required:!0,message:"请输入 6 位动态码!",pattern:/^\d{6}$/,len:6}]}}},[s("a-input",{style:{textAlign:"center"},attrs:{placeholder:"000000"},nativeOn:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.handleStepOk(e)}}})],1),s("p",{staticStyle:{"text-align":"center"}},[s("a",{on:{click:t.onForgeStepCode}},[t._v("遗失手机?")])])],1)])],1)],2)},o=[],c={props:{visible:{type:Boolean,default:!1}},data:function(){return{stepLoading:!1,form:null}},methods:{handleStepOk:function(){var e=this,t=this;this.stepLoading=!0,this.form.validateFields((function(a,s){a?(e.stepLoading=!1,e.$emit("error",{err:a})):setTimeout((function(){t.stepLoading=!1,t.$emit("success",{values:s})}),2e3)}))},handleCancel:function(){this.visible=!1,this.$emit("cancel")},onForgeStepCode:function(){}}},l=c,u=(a("e340"),a("2877")),p=Object(u["a"])(l,n,o,!1,null,"53c128de",null),d=p.exports,m=a("5880"),g=a("ca00"),f=a("7ded"),h={components:{TwoStepCaptcha:d},data:function(){return{customActiveKey:"tab1",loginBtn:!1,loginType:0,isLoginError:!1,requiredTwoStepCaptcha:!1,stepCaptchaVisible:!1,form:this.$form.createForm(this),state:{time:60,loginBtn:!1,loginType:0,smsSendBtn:!1}}},created:function(){},methods:Object(r["a"])({},Object(m["mapActions"])(["Login","Logout"]),{handleUsernameOrEmail:function(e,t,a){var s=this.state,i=/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;i.test(t)?s.loginType=0:s.loginType=1,a()},handleTabClick:function(e){this.customActiveKey=e},handleSubmit:function(e){var t=this;e.preventDefault();var a=this.form.validateFields,s=this.state,i=this.customActiveKey,n=this.Login;s.loginBtn=!0;var o="tab1"===i?["username","password"]:["mobile","captcha"];a(o,{force:!0},(function(e,a){if(e)setTimeout((function(){s.loginBtn=!1}),600);else{var i=Object(r["a"])({},a);delete i.username,i[s.loginType?"username":"email"]=a.username,i.password=a.password,n(i).then((function(e){return t.loginSuccess(e)})).catch((function(e){return t.requestFailed(e)})).finally((function(){s.loginBtn=!1}))}}))},getCaptcha:function(e){var t=this;e.preventDefault();var a=this.form.validateFields,s=this.state;a(["mobile"],{force:!0},(function(e,a){if(!e){s.smsSendBtn=!0;var i=window.setInterval((function(){s.time--<=0&&(s.time=60,s.smsSendBtn=!1,window.clearInterval(i))}),1e3),r=t.$message.loading("验证码发送中..",0);Object(f["b"])({mobile:a.mobile}).then((function(e){setTimeout(r,2500),t.$notification["success"]({message:"提示",description:"验证码获取成功，您的验证码为："+e.result.captcha,duration:8})})).catch((function(e){setTimeout(r,1),clearInterval(i),s.time=60,s.smsSendBtn=!1,t.requestFailed(e)}))}}))},stepCaptchaSuccess:function(){this.loginSuccess()},stepCaptchaCancel:function(){var e=this;this.Logout().then((function(){e.loginBtn=!1,e.stepCaptchaVisible=!1}))},loginSuccess:function(e){var t=this;this.$router.push({path:"/admin/"}),setTimeout((function(){t.$notification.success({message:"欢迎",description:"".concat(Object(g["a"])(),"，欢迎回来")})}),1e3),this.isLoginError=!1},requestFailed:function(e){this.isLoginError=!0,this.$notification["error"]({message:"错误",description:((e.response||{}).data||{}).message||"请求出现错误，请稍后再试",duration:4})}})},v=h,b=(a("f7b3"),Object(u["a"])(v,s,i,!1,null,"0178a906",null));t["default"]=b.exports},d726:function(e,t,a){},e340:function(e,t,a){"use strict";var s=a("d726"),i=a.n(s);i.a},f7b3:function(e,t,a){"use strict";var s=a("7cb0"),i=a.n(s);i.a}}]);