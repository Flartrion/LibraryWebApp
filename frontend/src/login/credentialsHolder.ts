class CredentialsHolder {
  cookies: [string, string][];

  addCookie(name: string, value: string, options: string): void {
    let newCookie =
      encodeURIComponent(name) +
      "=" +
      encodeURIComponent(value) +
      encodeURIComponent(options);
    document.cookie = newCookie;
    this.cookies.push([name, value]);
  }

  constructor() {
    const regExp = new RegExp("(?:^| )([a-zA-Z0-9.]+)=([^;]+)").exec(
      document.cookie
    );
    while (regExp.length > 0) {
      const value = regExp.pop();
      const name = regExp.pop();
      this.cookies.push([name, value]);
    }
  }
}

const credentialsHolder = new CredentialsHolder();
export default credentialsHolder;
