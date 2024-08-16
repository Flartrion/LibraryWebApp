import { memo, useMemo } from "react"
import ItemBalance from "../../../../dataclasses/itemBalance"
import {
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material"
import itemBalanceModel from "./itemBalanceModel"

interface ItemBalanceGridProps {
  items: ItemBalance[]
}

const ItemBalanceList = memo(
  ({ items }: ItemBalanceGridProps) => {
    /** For simplicity uses ItemBalance type also. IdStorage is replaced with address */
    const transformedItems: [ItemBalance, string][] = useMemo(() => {
      console.log("recalculated")
      return items.map((item) => {
        return [
          item,
          itemBalanceModel.storages.find(
            (storage) => storage.id == item.idStorage
          ).address,
        ]
      })
    }, [items])
    return (
      <TableContainer sx={{ flex: "1 1 auto" }} component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Entry ID</TableCell>
              <TableCell align="right">Storage</TableCell>
              <TableCell align="right">Created on</TableCell>
              <TableCell align="right">Change</TableCell>
            </TableRow>
          </TableHead>
          {transformedItems.map((value: [ItemBalance, string]) => {
            return (
              <TableRow>
                <TableCell>{value[0].id}</TableCell>
                <TableCell align="right">{value[1]}</TableCell>
                <TableCell align="right">{value[0].date}</TableCell>
                <TableCell align="right">{value[0].change}</TableCell>
              </TableRow>
            )
          })}
        </Table>
      </TableContainer>
    )
  },
  (a, b) => {
    if (a.items === b.items) return true
    if (a.items == null || b.items == null) return false
    if (a.items.length !== b.items.length) return false

    // If you don't care about the order of the elements inside
    // the array, you should sort both arrays here.
    // Please note that calling sort on an array will modify that array.
    // you might want to clone your array first.

    for (var i = 0; i < a.items.length; ++i) {
      if (a.items[i] !== b.items[i]) return false
    }
    return true
  }
)

export default ItemBalanceList
