const loginObjectTempName = {
  loginRequest(): void {
    
    headers.append("Content-Type","application/json")
    const options = {
        body: "",
        headers: {
            "Content-Type": "application/json"
        }
    }
    const request = new Request("/login")
    fetch(document.baseURI+"/login")
  },
};
