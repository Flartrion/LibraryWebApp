const loginObjectTempName = {
  loginRequest({ login, password }: any): void {
    const options = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Accept-Encoding": "application/json",
      },
      body: JSON.stringify({
        email: login,
        password: password,
      }),
    };
    console.log(JSON.stringify({
      email: login,
      password: password,
    }))
    const request = new Request("/login", options);
    const response = fetch(request);
    response.then((response) => {
      if (response.status == 200)
        response.json().then((jsonBody) => {
          console.log(response.statusText);
          console.log(jsonBody);
        });
      else {
        console.log(response.statusText);
        response.text().then((resText) => {
          console.log(resText);
        });
      }
    });
  },
};

export default loginObjectTempName;
