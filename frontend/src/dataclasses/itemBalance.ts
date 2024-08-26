type ItemBalance = Id & {
  idItem: string
  idStorage: string
  date: string
  change: string
  address: string
}

export default ItemBalance

export function newItemBalance(
  id: string = "",
  idItem: string = "",
  idStorage: string = "",
  date: string = "",
  change: string = "",
  address: string = ""
): ItemBalance {
  const retval = {} as ItemBalance
  retval.id = id
  retval.idItem = idItem
  retval.idStorage = idStorage
  retval.date = date
  retval.change = change
  retval.address = address
  return retval
}
