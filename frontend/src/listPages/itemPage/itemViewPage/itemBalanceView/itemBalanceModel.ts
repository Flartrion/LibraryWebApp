import Storage from "../../../../dataclasses/storage"

const itemBalanceModel: {
  storages: Storage[]
  storagesLoaded: boolean
  historyEntries: ItemBalance[]
  historyEntriesFiltered: ItemBalance[]
  id: string
} = {
  storages: undefined,
  storagesLoaded: false,
  id: undefined,
  historyEntries: undefined,
  historyEntriesFiltered: undefined,
}
export default itemBalanceModel
