import React, { memo, useMemo } from "react"
import ItemBalance from "../../../../dataclasses/itemBalance"
import {
  Button,
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material"
import itemBalanceModel from "./itemBalanceModel"
import { DeleteForeverOutlined } from "@mui/icons-material"

interface ItemBalanceGridProps {
  items: ItemBalance[]
  handleDelete: (id: string) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

const ItemBalanceList = ({ items, handleDelete }: ItemBalanceGridProps) => {
  /** Contains original item and, for now, address additionally */
  const transformedItems: [ItemBalance, string][] = useMemo(() => {
    // console.log("recalculated")
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
            <TableCell align="center">Actions</TableCell>
          </TableRow>
        </TableHead>
        {transformedItems.map((value: [ItemBalance, string]) => {
          return (
            <TableRow>
              <TableCell>{value[0].id}</TableCell>
              <TableCell align="right">{value[1]}</TableCell>
              <TableCell align="right">{value[0].date}</TableCell>
              <TableCell align="right">{value[0].change}</TableCell>
              <TableCell align="center">
                <Button onClick={handleDelete(value[0].id)}>
                  <DeleteForeverOutlined />
                </Button>
              </TableCell>
            </TableRow>
          )
        })}
      </Table>
    </TableContainer>
  )
}

export default ItemBalanceList
