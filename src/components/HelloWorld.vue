<template>
    <div class="vue-template">
        <form>
           <h3>Giriş</h3>

            


            <div class="form-group">
                <label>Email</label>
                <input type="email" class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Şifre</label>
                <input type="password" class="form-control form-control-lg" />
            </div>

            <b-button @click.stop="girisYap">
              Bas bana
            </b-button>

            <p class="forgot-password text-right mt-2 mb-4">
                <router-link to="/forgot-password">Parolanızı mı unuttunuz?</router-link>
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
            return {}
        },
        methods: {
          girisYap(){
            console.log("iii")
            let ibo = {
            "email": "halİT",
            "sifre": "ibok"
          }
          axios.
            post("http://localhost:8080/api/giris", ibo)
              .then(response => {
                console.log("gelenDeğer: ", response)
                if (response.status == 202)
                  this.$router.push('/admin/videos');
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