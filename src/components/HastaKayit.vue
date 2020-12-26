<template>
    <div class="vue-template">
        <form>
           <h3>Hasta Kayıt Ekranı</h3>

            

            <div class="form-group">
                <label>İsim</label>
                <input type="input" v-model=isim  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Soyisim</label>
                <input type="input" v-model=soyisim  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>TC</label>
                <input type="input" v-model=TC  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Doğum Tarihi</label>
                <input type="input" v-model=dogumTarihi  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Telefon Numarası</label>
                <input type="input" v-model=telefon  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Adres</label>
                <input type="input" v-model=adres  class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="email" v-model=email class="form-control form-control-lg" />
            </div>

            <div class="form-group">
                <label>Şifre</label>
                <input type="password" v-model=sifre  class="form-control form-control-lg" />
            </div>


            <b-button @click.stop="kaydol">
              Kaydol
            </b-button>

    

        </form>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        data() {
            return {
                isim: "",
                soyisim: "",
                TC: "",
                dogumTarihi: "",
                telefon: "",
                adres: "",
                email: "",
                sifre: "",
                options: [
                    { value: null, text: 'Please select an option' },
                    { value: 'hasta', text: 'Hasta Giriş' },
                    { value: 'doktor', text: 'Doktor Giriş' },
                    { value: 'tetkikGorevlisi', text: 'Tetkik Görevlisi Giriş'},
                    { value: "admin", text: 'Admin Giriş' }
                ],
            }
        },
        methods: {
          kaydol(){
            let data = {
                "isim": this.isim,
                "soyisim": this.soyisim,
                "TC": this.TC,
                "dogumTarihi": this.dogumTarihi,
                "telefon": this.telefon,
                "adres": this.adres,
                "email": this.userEmail,
                "sifre": this.userPassword
          }
          var kayitURL = "http://localhost:8080/api/hastaKayit";
          axios.
            post(kayitURL, data)
              .then(response => {
                console.log("gelenDeğer: ", response)
                let data = response.data;

                if (response.status == 202)
                  this.$router.push('/');
                else{
                    alert("eksik bilgi")
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