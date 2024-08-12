type User = Id & {
  role: string
  fullName: string
  dob: string
  phoneNumber: string
  email: string
}
export default User

export function newUser(
  id?: string,
  role?: string,
  fullName?: string,
  dob?: string,
  phoneNumber?: string,
  email?: string
): User {
  const retval: User = {} as User
  retval.id = id != undefined ? id : ""
  retval.role = role != undefined ? role : ""
  retval.fullName = fullName != undefined ? fullName : ""
  retval.dob = dob != undefined ? dob : ""
  retval.phoneNumber = phoneNumber != undefined ? phoneNumber : ""
  retval.email = email != undefined ? email : ""
  return retval
}
