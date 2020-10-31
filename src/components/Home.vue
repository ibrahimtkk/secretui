<template>
  <div>
    <ul class="list-group list-group-flush">
            <li class="list-group-item d-flex justify-content-between align-items-center"
                v-for="(item, index) in photos">
              <div class="d-flex justify-content-between align-items-center">
                <div style="margin-left: 15px">
                  <small>{{item}}</small>
                </div>
              </div>
            </li>
          </ul>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      photos: [],
    }
  },
  created(){
    
    var username = localStorage.getItem("username")

    /* download image */
    axios
      .post('http://localhost:8080/api/download/' + username) 
        .then((response) => {
          this.photos = response.data;
          console.log("photos: ", response.data)
        }).catch((error) => {
            console.log("erroreytion: ", error);
        });

    console.log("usernames: ", username)
    axios
      .get('http://localhost:8080/api/photos/' + username) 
        .then((response) => {
          this.photos = response.data;
          console.log("photos: ", this.photos)
        }).catch((error) => {
            console.log("erroreytion: ", error);
        });
  }
}
</script>