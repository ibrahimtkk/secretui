<template>
    <div class="vue-template">
        <form>
           <h3>Giriş</h3>

            


            <div class="form-group">
                <label>Email</label>
                <input type="email" v-model=userEmail class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Şifre</label>
                <input type="password" v-model=userPassword  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Giriş Türü</label>
                <b-form-select v-model="selectedLoginType" :options="options"></b-form-select>
            </div>

            <br>
            <b-button @click.stop="girisYap" variant="primary">
              Giriş Yap
            </b-button>

            <p class="forgot-password text-right mt-2 mb-4">
                <router-link to="/forgot-password">Parolanızı mı unuttunuz?</router-link>
            </p>

            <p class="hasta-kayit text-right mt-2 mb-4">
                <router-link to="/hastaKayit">Kaydol</router-link>
            </p>

            <!--<div class="social-icons">
                <ul>
                    <li><a href="#"><i class="fa fa-google"></i></a></li>
                    <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                    <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                </ul>
            </div>-->

        </form>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        data() {
            return {
                selectedLoginType: null,
                userEmail: "",
                userPassword: "",
                options: [
                    { value: null, text: 'Please select an option' },
                    { value: 'hasta', text: 'Hasta Giriş' },
                    { value: 'doktor', text: 'Doktor Giriş' },
                    { value: 'tetkikGorevlisi', text: 'Tetkik Görevlisi Giriş'},
                    { value: "admin", text: 'Admin Giriş' }
                ]
            }
        },
        methods: {
          girisYap(){
            console.log("iii")
            let userInfo = {
                "email": this.userEmail,
                "sifre": this.userPassword,
                "type":  this.selectedLoginType
          }
          var loginURL = "http://localhost:8080/api/login";
          axios.
            post(loginURL, userInfo)
              .then(response => {
                console.log("gelenDeğer: ", response)
                let data = response.data;

                if (this.selectedLoginType === "hasta" && response.status == 202)
                  this.$router.push('/hastaHome');
                else if (this.selectedLoginType === "dostor" && response.status == 202)
                  this.$router.push('/doktorHome');
                if (this.selectedLoginType === "admin" && response.status == 202)
                  this.$router.push('/adminHome');
                if (this.selectedLoginType === "tetkikGorevlisi" && response.status == 202)
                  this.$router.push('/tetkikGorevlisiHome');
                else {
                    alert("hatalı giriş");
                }
              })
              .catch(error => {
                console.log("gelenDeğerError: ", error)
              })
          }
        },
        created(){
          console.log("dsadsadasdad")
          
        }
    }
</script>