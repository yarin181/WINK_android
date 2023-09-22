import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/js/bootstrap.js';
import GenericComponent from "./general_components/genericComponent"
import RegisterPage from "./register_components/RegisterPage";
import Login from "./login_components/Login"
import Chat from "./chat_components/Chat"
import {BrowserRouter, Route, Routes, Navigate} from 'react-router-dom';
import React, {useState} from "react";



function App(){
    const [usersList, setUsersList] = useState([]);
    const [isConnected,setIsConnected]=useState(false)
    const [currentUser, setCurrentUser]=useState({})
    const [token,setToken]=useState('')

  return (

      <BrowserRouter>
          <GenericComponent />
          <Routes>
              <Route path="/" element={ <Login setToken={setToken} usersList={usersList} setIsConnected={setIsConnected} setCurrentUser={setCurrentUser}/>} />
              <Route path="/registerPage" element={<RegisterPage />} />
              <Route path="/chat" element={isConnected ? (<Chat token={token} currentUser={currentUser} setIsConnected={setIsConnected} />) :   (<Navigate to="/" replace={true} />) } />
          </Routes>
      </BrowserRouter>

  );
}

export default App;
