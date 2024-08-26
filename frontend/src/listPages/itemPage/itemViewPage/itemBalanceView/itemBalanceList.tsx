import React from "react"
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
import { DeleteForeverOutlined } from "@mui/icons-material"

interface ItemBalanceGridProps {
  items: ItemBalance[]
  handleDelete: (id: string) => (e: React.MouseEvent<HTMLButtonElement>) => void
}

const ItemBalanceList = ({ items, handleDelete }: ItemBalanceGridProps) => {
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
        {items.map((value: ItemBalance) => {
          return (
            <TableRow>
              <TableCell>{value.id}</TableCell>
              <TableCell align="right">{value.address}</TableCell>
              <TableCell align="right">{value.date}</TableCell>
              <TableCell align="right">{value.change}</TableCell>
              <TableCell align="center">
                <Button onClick={handleDelete(value.id)}>
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
