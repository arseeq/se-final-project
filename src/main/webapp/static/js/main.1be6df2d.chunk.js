(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{35:function(e,t,a){e.exports=a(64)},64:function(e,t,a){"use strict";a.r(t);var n=a(1),l=a.n(n),r=a(17),i=a.n(r),s=a(9),c=a(10),o=a(11),m=a(12),u=a(13),h=a(67),d=a(69),p=a(68),g=a(66),E=a(18),b=a.n(E),f="",v=(n.Component,a(8)),O=a(3),j=a(65),k=function(e){function t(e){var a;return Object(s.a)(this,t),(a=Object(o.a)(this,Object(m.a)(t).call(this,e))).toggle=a.toggle.bind(Object(v.a)(Object(v.a)(a))),a.state={isOpen:!1},a}return Object(u.a)(t,e),Object(c.a)(t,[{key:"toggle",value:function(){this.setState({isOpen:!this.state.isOpen})}},{key:"render",value:function(){return l.a.createElement(O.o,{color:"light",light:!0,expand:"md"},l.a.createElement(O.p,{href:"/"},"AIntrovert"),l.a.createElement(O.q,{onClick:this.toggle}),l.a.createElement(O.e,{isOpen:this.state.isOpen,navbar:!0},l.a.createElement(O.m,{className:"ml-auto",navbar:!0},l.a.createElement(O.r,{nav:!0,inNavbar:!0},l.a.createElement(O.h,{nav:!0,caret:!0},"Options"),l.a.createElement(O.g,{right:!0},l.a.createElement(O.f,null,"Option 1"),l.a.createElement(O.f,null,"Option 2"),l.a.createElement(O.f,{divider:!0}),l.a.createElement(O.f,null,"Reset"))),!this.props.auth&&l.a.createElement(l.a.Fragment,null,"signin"!=this.props.id?l.a.createElement(O.n,null,l.a.createElement(j.a,{className:"nav",to:"/signin"},"Sign In")):"","signup"!=this.props.id?l.a.createElement(O.n,null,l.a.createElement(j.a,{to:"/signup"},"Sign Up")):""),this.props.auth&&"dashboard"!=this.props.id&&l.a.createElement(l.a.Fragment,null,l.a.createElement(O.n,null,l.a.createElement(j.a,{to:"/dashboard"},"Dashboard"))),this.props.auth&&l.a.createElement(l.a.Fragment,null,l.a.createElement(O.n,{className:"btn btn-link",onClick:this.props.logout},"Sign Out")))))}}]),t}(n.Component),y=function(e){function t(e){var a;return Object(s.a)(this,t),(a=Object(o.a)(this,Object(m.a)(t).call(this,e))).state={email:"",password:""},a.signin=a.signin.bind(Object(v.a)(Object(v.a)(a))),a.handleChange=a.handleChange.bind(Object(v.a)(Object(v.a)(a))),a}return Object(u.a)(t,e),Object(c.a)(t,[{key:"signin",value:function(){var e=this;b()(f+"/mainServices/auth/signin",{method:"POST",data:JSON.stringify({email:e.state.email,password:e.state.password}),headers:{"Content-Type":"application/json"}}).then(function(t){localStorage.setItem("token",t.data),e.props.login(e.state.email)}).catch(function(e){console.log(e)})}},{key:"handleChange",value:function(e){switch(e.target.name){case"email":this.setState({email:e.target.value});break;case"password":this.setState({password:e.target.value})}}},{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement(k,{id:"signin",auth:!1}),l.a.createElement("div",{className:"container text-center",style:{marginTop:"20px",width:"500px"}},l.a.createElement("div",{className:"row"},l.a.createElement("h1",{style:{marginLeft:"190px"}},"Sign In")),l.a.createElement(O.i,{className:"form",style:{marginTop:"80px",marginBottom:"80px"}},l.a.createElement(O.d,null,l.a.createElement(O.j,null,l.a.createElement(O.l,null,"Email"),l.a.createElement(O.k,{onChange:this.handleChange,type:"email",name:"email",id:"exampleEmail",value:this.state.email}))),l.a.createElement(O.d,null,l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"examplePassword"},"Password"),l.a.createElement(O.k,{onChange:this.handleChange,type:"password",name:"password",id:"examplePassword",value:this.state.password}))),l.a.createElement(O.a,{onClick:this.signin},"Submit"))))}}]),t}(n.Component),S=function(e){function t(e){var a;return Object(s.a)(this,t),(a=Object(o.a)(this,Object(m.a)(t).call(this,e))).state={name:"",surname:"",email:"",password:""},a.signup=a.signup.bind(Object(v.a)(Object(v.a)(a))),a.handleChange=a.handleChange.bind(Object(v.a)(Object(v.a)(a))),a}return Object(u.a)(t,e),Object(c.a)(t,[{key:"signup",value:function(){var e=this;b()(f+"/mainServices/auth/signup",{method:"POST",data:JSON.stringify({name:e.state.name,surname:e.state.surname,email:e.state.email,password:e.state.password}),headers:{"Content-Type":"application/json"}}).then(function(t){console.log(t),localStorage.setItem("token",t.data),e.props.login(e.state.email)}).catch(function(e){console.log(e)})}},{key:"handleChange",value:function(e){switch(e.target.name){case"name":this.setState({name:e.target.value});break;case"surname":this.setState({surname:e.target.value});break;case"email":this.setState({email:e.target.value});break;case"password":this.setState({password:e.target.value})}}},{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement(k,{id:"signup",auth:!1}),l.a.createElement("div",{className:"container text-center",style:{paddingTop:"20px",width:"500px"}},l.a.createElement("div",{className:"row"},l.a.createElement("h2",{style:{marginLeft:"80px",marginBottom:"20px"}},"Registratsiya zhasa")),l.a.createElement(O.i,{className:"text-left",style:{marginTop:"80px",marginBottom:"80px"}},l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"name"},"Name"),l.a.createElement(O.k,{type:"text",onChange:this.handleChange,name:"name",id:"name",value:this.state.name})),l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"surname"},"Surname"),l.a.createElement(O.k,{type:"text",onChange:this.handleChange,name:"surname",id:"surname",value:this.state.surname})),l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"exampleEmail"},"Email"),l.a.createElement(O.k,{type:"email",onChange:this.handleChange,name:"email",id:"exampleEmail",value:this.state.email})),l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"examplePassword"},"Password"),l.a.createElement(O.k,{type:"password",onChange:this.handleChange,name:"password",id:"examplePassword",value:this.state.password})),l.a.createElement(O.j,null,l.a.createElement(O.l,{for:"exampleSelect"},"Select"),l.a.createElement(O.k,{type:"select",name:"select",id:"exampleSelect"},l.a.createElement("option",null,"Male"),l.a.createElement("option",null,"Mambet"),l.a.createElement("option",null,"Female"))),l.a.createElement(O.j,{check:!0},l.a.createElement(O.l,{check:!0},l.a.createElement(O.k,{type:"checkbox"})," ","Remember that I am mambet")),l.a.createElement(O.a,{onClick:this.signup,style:{marginTop:"20px"}},"Submit"))))}}]),t}(n.Component),w=function(e){function t(e){return Object(s.a)(this,t),Object(o.a)(this,Object(m.a)(t).call(this,e))}return Object(u.a)(t,e),Object(c.a)(t,[{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement(k,{id:"dashboard",auth:!0,logout:this.props.logout}),l.a.createElement("h1",null,"Dashboard Page. Wellcome, ",this.props.user,"!"))}}]),t}(n.Component),x=function(e){function t(){return Object(s.a)(this,t),Object(o.a)(this,Object(m.a)(t).apply(this,arguments))}return Object(u.a)(t,e),Object(c.a)(t,[{key:"render",value:function(){return l.a.createElement("div",null,l.a.createElement(O.b,null,l.a.createElement(O.c,{className:"text-muted text-center"},"\xa9 2018 ",l.a.createElement("span",{style:{color:"#0000ff"}},"AIntrovert")," All Rights Reserved")))}}]),t}(n.Component),C=function(e){function t(e){var a;return Object(s.a)(this,t),(a=Object(o.a)(this,Object(m.a)(t).call(this,e))).state={authorized:null,user:""},a}return Object(u.a)(t,e),Object(c.a)(t,[{key:"componentWillMount",value:function(){var e=this;null!=localStorage.getItem("token")?b()(f+"/mainServices/auth/checktoken",{method:"POST",data:JSON.stringify({token:localStorage.getItem("token")}),headers:{"Content-Type":"application/json"}}).then(function(t){console.log(t.data),e.login(t.data)}).catch(function(t){console.log(t),e.setState({authorized:!1})}):e.setState({authorized:!1})}},{key:"login",value:function(e){console.log(e),this.setState({authorized:!0,user:e})}},{key:"logout",value:function(){localStorage.setItem("token",""),this.setState({authorized:!1})}},{key:"render",value:function(){var e=this,t=this;return null==this.state.authorized?l.a.createElement("h1",null,"Loading ... "):l.a.createElement("div",null,l.a.createElement(h.a,null,l.a.createElement(d.a,null,l.a.createElement(p.a,{exact:!0,path:"/dashboard",render:function(){return t.state.authorized?l.a.createElement(w,{user:t.state.user,logout:t.logout.bind(e)}):l.a.createElement(g.a,{to:"/signin"})}}),l.a.createElement(p.a,{exact:!0,path:"/signin",render:function(){return t.state.authorized?l.a.createElement(g.a,{to:"/dashboard"}):l.a.createElement(y,{login:t.login.bind(e)})}}),l.a.createElement(p.a,{exact:!0,path:"/signup",render:function(){return t.state.authorized?l.a.createElement(g.a,{to:"/dashboard"}):l.a.createElement(S,{login:t.login.bind(e)})}}),l.a.createElement(p.a,{path:"/",render:function(){return l.a.createElement(g.a,{to:"/signin"})}}))),l.a.createElement(x,null))}}]),t}(l.a.Component);a(62);i.a.render(l.a.createElement(C,null),document.getElementById("root"))}},[[35,2,1]]]);
//# sourceMappingURL=main.1be6df2d.chunk.js.map