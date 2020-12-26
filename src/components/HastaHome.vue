<template>
    <div class="vue-template">
        <form>
           <h3>Randevu Al</h3>

            


            <div class="form-group">
                <label>Birim</label>
                <b-form-select v-model="selectedKlinik" :options="klinikler"></b-form-select>
            </div>

            <div class="form-group">
                <label>Doktor</label>
                <b-form-select v-model="selectedDoktor" :options="doktorlar"></b-form-select>
            </div>

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
                selectedKlinik: null,
                selectedDoktor: null,
                userEmail: "",
                userPassword: "",
                options: [
                    { value: null, text: 'Please select an option' },
                    { value: 'hasta', text: 'Hasta Giriş' },
                    { value: 'doktor', text: 'Doktor Giriş' },
                    { value: 'tetkikGorevlisi', text: 'Tetkik Görevlisi Giriş'},
                    { value: "admin", text: 'Admin Giriş' }
                ],
                klinikler: [
                  { value: null, text: 'Please select an option' },
                  { value: 'uroloji', text: 'Üroloji' },
                  { value: 'kardiyoloji', text: 'Kardiyoloji' },
                  { value: 'noroloji', text: 'Nöroloji' },
                ],
                doktorlar: []
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