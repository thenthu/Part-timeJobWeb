// src/configs/Firebase.js
import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyA-nRPCaSjthhs-0_6Vig7bJC2Xh-pqW1Q",
  authDomain: "joba-930de.firebaseapp.com",
  databaseURL: "https://joba-930de-default-rtdb.asia-southeast1.firebasedatabase.app",
  projectId: "joba-930de",
  storageBucket: "joba-930de.appspot.com",
  messagingSenderId: "123509038439",
  appId: "1:123509038439:web:8475f3d414c1700a5163c2",
  measurementId: "G-C9SFBDCG98"
};

const app = initializeApp(firebaseConfig);

const db = getDatabase(app);

export { db };
