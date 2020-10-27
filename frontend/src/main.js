import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import HNav from "./components/common/HNav.vue";
import HBreadcumb from "./components/common/HBreadcrumb.vue";
import vuetify from "./plugins/vuetify";
import VueMaterial from "vue-material";
import "vue-material/dist/vue-material.css";
import "vue-material/dist/theme/black-green-light.css";
import * as firebase from "firebase/app";
import "firebase/storage";

var firebaseConfig = {
  apiKey: "AIzaSyBLERzEBv4p5978AxszC7zqy8aQXXfxCY8",
  authDomain: "ssafy2.firebaseapp.com",
  databaseURL: "gs://ssafy2.appspot.com",
  projectId: "ssafy2",
  storageBucket: "gs://ssafy2.appspot.com",
  messagingSenderId: "435147211758",
  appId: "1:435147211758:web:c225ef95ebbd44f73284ff",
  measurementId: "G-M80C8DTDRF",
};
// Initialize Firebase
firebase.initializeApp(firebaseConfig);

Vue.filter("truncate", function (text, length, clamp) {
  if (text) {
    clamp = clamp || "...";
    return text.length > length ? text.slice(0, length) + clamp : text;
  }
});

new Vue({
  el: "#app",
  router,
  store,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");

Vue.component("HNav", HNav);
Vue.component("HBreadcrumb", HBreadcumb);

var cors = require("cors");

App.use(cors());
