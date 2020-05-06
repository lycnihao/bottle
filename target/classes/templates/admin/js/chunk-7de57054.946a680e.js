(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7de57054"],{"306f":function(t,a,e){"use strict";e.r(a);var s=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("a-row",{staticStyle:{height:"100vh"},attrs:{type:"flex",justify:"center",align:"middle"}},[e("a-col",{attrs:{xl:8,md:12,sm:20,xs:24}},[e("div",{staticClass:"card-container"},[e("a-card",{staticStyle:{"box-shadow":"0px 10px 20px 0px rgba(236, 236, 236, 0.86)"},attrs:{bordered:!1,title:"Bottle 安装向导"}},[e("a-steps",{attrs:{current:t.stepCurrent}},[e("a-step",{attrs:{title:"用户信息"}}),e("a-step",{attrs:{title:"系统信息"}}),e("a-step",{attrs:{title:"安装完成"}})],1),e("a-divider",{attrs:{dashed:""}}),e("a-form",{directives:[{name:"show",rawName:"v-show",value:0==t.stepCurrent,expression:"stepCurrent == 0"}],attrs:{layout:"horizontal",form:t.bottleForm}},[e("a-form-item",{staticClass:"animated fadeInUp"},[e("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["username",{rules:[{required:!0,message:"请输入用户名"}]}],expression:"[\n                    'username',\n                    {rules: [{ required: true, message: '请输入用户名' }]}\n                  ]"}],attrs:{placeholder:"用户名"},model:{value:t.installation.username,callback:function(a){t.$set(t.installation,"username",a)},expression:"installation.username"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"user"},slot:"prefix"})],1)],1),e("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.1s"}},[e("a-input",{attrs:{placeholder:"用户昵称"},model:{value:t.installation.nickname,callback:function(a){t.$set(t.installation,"nickname",a)},expression:"installation.nickname"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"smile"},slot:"prefix"})],1)],1),e("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.2s"}},[e("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["email",{rules:[{required:!0,message:"请输入邮箱"},{type:"email",message:"邮箱格式不正确!"}]}],expression:"[\n                    'email',\n                    {rules: [{ required: true, message: '请输入邮箱' },{ type: 'email',message: '邮箱格式不正确!' }]}\n                  ]"}],attrs:{placeholder:"用户邮箱"},model:{value:t.installation.email,callback:function(a){t.$set(t.installation,"email",a)},expression:"installation.email"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"mail"},slot:"prefix"})],1)],1),e("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.3s"}},[e("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["password",{rules:[{required:!0,message:"请输入密码（8-100位）"},{validator:t.handleValidatePassword}]}],expression:"[\n                    'password',\n                    {rules: [{ required: true, message: '请输入密码（8-100位）' },{ validator: handleValidatePassword }]}\n                  ]"}],attrs:{type:"password",placeholder:"用户密码（8-100位）"},model:{value:t.installation.password,callback:function(a){t.$set(t.installation,"password",a)},expression:"installation.password"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"lock"},slot:"prefix"})],1)],1),e("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.4s"}},[e("a-input",{directives:[{name:"decorator",rawName:"v-decorator",value:["confirmPassword",{rules:[{required:!0,message:"请输入确认密码"},{validator:t.handleValidateConfirmPassword}]}],expression:"[\n                    'confirmPassword',\n                    {rules: [{ required: true, message: '请输入确认密码' },{ validator: handleValidateConfirmPassword }]}\n                  ]"}],attrs:{type:"password",placeholder:"确认密码"},model:{value:t.installation.confirmPassword,callback:function(a){t.$set(t.installation,"confirmPassword",a)},expression:"installation.confirmPassword"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"lock"},slot:"prefix"})],1)],1)],1),e("a-form",{directives:[{name:"show",rawName:"v-show",value:1==t.stepCurrent,expression:"stepCurrent == 1"}],attrs:{layout:"horizontal"}},[e("a-form-item",{staticClass:"animated fadeInUp"},[e("a-input",{attrs:{placeholder:"网站地址"},model:{value:t.installation.url,callback:function(a){t.$set(t.installation,"url",a)},expression:"installation.url"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"link"},slot:"prefix"})],1)],1),e("a-form-item",{staticClass:"animated fadeInUp",style:{"animation-delay":"0.2s"}},[e("a-input",{attrs:{placeholder:"网站标题"},model:{value:t.installation.title,callback:function(a){t.$set(t.installation,"title",a)},expression:"installation.title"}},[e("a-icon",{staticStyle:{color:"rgba(0,0,0,.25)"},attrs:{slot:"prefix",type:"book"},slot:"prefix"})],1)],1)],1),e("a-row",{staticClass:"install-action",staticStyle:{"margin-top":"1rem"},attrs:{type:"flex",justify:"space-between"}},[e("div",[0!=t.stepCurrent?e("a-button",{staticClass:"previus-button",staticStyle:{"margin-right":"1rem"},on:{click:function(a){t.stepCurrent--}}},[t._v("上一步")]):t._e(),1!=t.stepCurrent?e("a-button",{attrs:{type:"primary"},on:{click:t.handleNextStep}},[t._v("下一步")]):t._e()],1),1==t.stepCurrent?e("a-button",{attrs:{type:"primary",icon:"upload",loading:t.installing},on:{click:t.handleInstall}},[t._v("安装")]):t._e()],1)],1)],1)])],1)],1)},i=[],n=(e("d3b7"),e("50fc")),l={data:function(){return{installation:{},stepCurrent:0,bottleForm:this.$form.createForm(this),installing:!1}},created:function(){this.verifyIsInstall(),this.$set(this.installation,"url",window.location.protocol+"//"+window.location.host)},methods:{handleValidateConfirmPassword:function(t,a,e){var s=this.bottleForm;a&&a!==s.getFieldValue("password")&&e("确认密码和密码不匹配"),e()},handleValidatePassword:function(t,a,e){a&&a.length<8&&e("密码不能低于 8 位"),e()},verifyIsInstall:function(){var t=this;n["a"].isInstalled().then((function(a){a&&t.$router.push({name:"Login"})}))},handleNextStep:function(t){var a=this;t.preventDefault(),this.bottleForm.validateFields((function(t,e){null!=t||a.stepCurrent++}))},install:function(){var t=this;n["a"].install(this.installation).then((function(a){t.$message.success("安装成功！"),setTimeout((function(){t.$router.push({name:"Login"})}),300)})).finally((function(){t.installing=!1}))},handleInstall:function(){var t=this.installation.password,a=this.installation.confirmPassword;t===a?(this.installing=!0,this.install()):this.$message.error("确认密码和密码不匹配")}}},r=l,o=e("2877"),c=Object(o["a"])(r,s,i,!1,null,null,null);a["default"]=c.exports},"50fc":function(t,a,e){"use strict";var s=e("b775"),i="http://106.54.255.9/:8080/api/admin",n={isInstalled:function(){return Object(s["b"])({url:"".concat(i,"/is_installed"),method:"get"})},install:function(t){return Object(s["b"])({url:"".concat(i,"/installations"),data:t,method:"post"})},login:function(t,a){return Object(s["b"])({url:"".concat(i,"/login"),data:{username:t,password:a},method:"post"})},logout:function(){return Object(s["b"])({url:"".concat(i,"/logout"),method:"post"})}};a["a"]=n}}]);