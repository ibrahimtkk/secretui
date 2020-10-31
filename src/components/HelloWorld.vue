<template>
  <div>
    <b-button variant="primary" @click.stop="girisButon">
    </b-button>
  </div>
  <!-- <div class="app flex-row align-items-center">
    <div class="container"> -->
      <!-- <div v-if="loginType==='userpass'">
        <div v-if="selected== 'standart'">
          <b-input-group class="mb-3 mt-3">
            <b-input-group-prepend>
              <b-input-group-text>
                <i class="icon-user"></i>
              </b-input-group-text>
            </b-input-group-prepend>
            <b-form-input
              class="form-control"
              placeholder="Username"
              type="text"
              autocomplete="username email"
              v-model.trim="formdata.username"
            ></b-form-input>
            
          </b-input-group>
          <b-input-group class="mb-3">
            <b-input-group-prepend>
              <b-input-group-text>
                <i class="icon-lock"></i>
              </b-input-group-text>
            </b-input-group-prepend>
            <b-form-input
              class="form-control"
              placeholder="Password"
              type="password"
              v-model.trim="formdata.password"
            ></b-form-input>
          </b-input-group>
          <b-row align-h="center">
            <b-col>
              <b-button variant="primary" @click.stop="girisButon"> 
                <b-spinner v-if="loginStatus" small type="grow">
                </b-spinner> 
                Login 
              </b-button>
            </b-col>
          </b-row>
        </div>
      </div> -->
      
      

      <!-- <b-button @click.stop="girisButon">
          Bas bana
      </b-button>
    </div> -->
  <!-- </div> -->
</template>

<script>
import axios from "axios";

export default {
  name: 'HelloWorld',
  // props: {
  //   msg: String
  // },
  // data() {
  //     return {
  //       username: '',
  //       password: '',
  //       formdata: {
  //         username: '',
  //         password: ''
  //       },
  //       loginType: 'userpass',
  //       selected: 'standart',
  //       loginStatus: ''
  //     }
  //   },
    methods: {
      girisButon(){
        console.log("basildi")
        const config = {
          headers: {
            'Content-Type': 'application/json'
          },
          data: {},
        };
        axios.
          get("http://localhost:8080/api/basic", config)
            .then(response => {
              console.log("response: ", response);
            })
            .catch(error => {
              console.log("error: ", error)
            })
      }
    },
    // methods: {
    //   girisButon(){
    //     console.log("123123123: ", this.formdata)
    //     this.loginStatus = true;
    //     var username = this.formdata.username.toLowerCase();
    //     this.formdata.username = username;
    //     axios
    //       .post("http://localhost:8080/api/logins", this.formdata)
    //         .then(response => {
    //           localStorage.setItem("username", this.formdata.username);
    //           console.log("resss: ", this.formdata);
    //           this.$router.push(this.$route.query.redirect || '/home');
    //           })
    //         .catch(error => {
    //           if (error.status == 400){
    //             this.loginStatus = false;
    //             console.log("errör400: ", error);
    //           }
    //           else{
    //             this.$toasted.show(`Wrong username/password!`, {
    //                         title: 'Session Notification',
    //                         variant: 'danger',
    //                         duration: 3000
    //                     });
    //             console.log("err: ", error);
    //           }
    //         });      
    //   }
    // },
    created() {
      console.log("123123");
    }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
