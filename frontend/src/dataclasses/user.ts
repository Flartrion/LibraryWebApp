export class User {
  idUser: string;
  role: number;
  fullName: string;
  dateOfBirth: string;
  phoneNumber: string;
  email: string;

  constructor(
    idUser: string,
    role: number,
    fullName: string,
    dateOfBirth: string,
    phoneNumber: string,
    email: string
  ) {
    this.idUser = idUser;
    this.role = role;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.phoneNumber = phoneNumber;
    this.email = email;
  }

  toJson = (): string => {
    return JSON.stringify(this)
  }
}
