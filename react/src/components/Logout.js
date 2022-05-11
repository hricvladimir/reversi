function Logout({setLoggedIn}) {

    setLoggedIn('');
    console.log('logged out');
    window.location.href = "http://localhost:3000";
}

export default Logout;