<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <div v-if="loginType==='userpass'">
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
                ></b-form-input>
              </b-input-group>
              <b-row align-h="center">
                <b-col>
                  <b-button variant="primary" @click.stop="girisButon" :disabled="loginStatus"> 
                    <b-spinner v-if="loginStatus" small type="grow">
                    </b-spinner> 
                    Login 
                  </b-button>
                </b-col>
              </b-row>
            </div>
          </div>
      
      

      <b-button @click.stop="girisButon">
          Bas bana
      </b-button>
    </div>
  </div>
</template>

<script>

export default {
    name: "Login",
    data() {
      return {
        username: '',
        password: '',
        formdata: {
          username: '',
          password: ''
        },
        loginType: 'userpass',
        selected: 'standart',
        loginStatus: '',
        photos: [],
      }
    },
    methods: {
      girisButon(){
        console.log("123123123: ", this.formdata)
        this.loginStatus = true;
        var username = this.formdata.username.toLowerCase();
        this.formdata.username = username;
        /* login check */
        axios
          .post("http://localhost:8080/api/logins", this.formdata)
          .then(response => {
            console.log("resss: ", response);
          })
          .catch(error => {
            console.log("hataaa");
            this.loginStatus = false;
            console.log("errör: ", error);
            if (error.response.status == 503) {
              //this.$router.push(this.$route.query.redirect || "/seal");
            } else {
              this.$bvToast.toast(`Username or password is wrong!`, {
                title: "Login Notification",
                variant: "danger",
                autoHideDelay: 5000
              });
            }
            localStorage.removeItem("user-token");
          });
      
      }
    },
    created() {
      console.log("123123");
    }
}
</script>