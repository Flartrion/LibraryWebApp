type GenericListModel<T extends Id> = {
  items: T[]
  loaded: boolean
  selection: number
  scrollOffset: number
}
