type Rent = Id & {
  idUser: string
  idItem: string
  idStorage: string
  dateFrom: string
  dateUntil: string
  dateStatus: string
  status: string
}

export default Rent

export const newRent = (
  id: string = "",
  idUser: string = "",
  idStorage: string = "",
  dateFrom: string = "",
  dateUntil: string = "",
  dateStatus: string = "",
  status: string = ""
) => {
  const retval = {} as Rent
  retval.id = id
  retval.idUser = idUser
  retval.idStorage = idStorage
  retval.dateFrom = dateFrom
  retval.dateUntil = dateUntil
  retval.dateStatus = dateStatus
  retval.status = status
  return retval
}
