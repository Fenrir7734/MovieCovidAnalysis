<template>
  <nav class="navbar navbar-expand-lg navbar-light navbar-green navbar-white" style="background-color: white">
    <div class="container-fluid">
      <router-link to="/" class="navbar-brand brand"><span  style="color: #6D6E70; font-weight: 200">MovieCovid</span><span class="text-primary">Analysis</span></router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse w-100 order-3 d-flex justify-content-end" id="navbarScroll">
        <ul class="navbar-nav ml-auto d-flex">
          <li v-if="loggedIn" class="d-flex nav-item">
            <a href="#" style="cursor: default;" class="nav-link nav-text-color">Hi <b>{{ currentUserUsername }}</b>!</a>
          </li>
          <li v-if="loggedIn" class="d-flex nav-item" style="cursor: pointer">
            <a class="nav-link" @click="logout">Log out</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: "NavBar",
  methods: {
    logout() {
      this.$store.dispatch('auth/logout')
          .then(() => {
            this.$router.push("/signin")
          })
          .catch(error => {
            console.log(error)
          })
    }
  }
}
</script>

<style scoped>
.navbar-white {
  background-color: white;
  border-bottom: 1px solid lightgray;
}
.brand {
  font-size: 24px;
  font-weight: bold;
}
</style>