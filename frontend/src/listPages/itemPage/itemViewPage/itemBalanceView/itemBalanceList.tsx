import { memo } from "react"
import ItemBalance from "../../../../dataclasses/itemBalance"
import { Box } from "@mui/material"
import { DataGrid, GridColDef, GridRowsProp } from "@mui/x-data-grid"

interface ItemBalanceGridProps {
  items: ItemBalance[]
}

const ItemBalanceList = memo(({ items }: ItemBalanceGridProps) => {
  const cols: GridColDef[] = [
    { field: "idOwn", headerName: "Entry ID", width: 270 },
    { field: "idStorage", headerName: "Storage ID", width: 270 },
    { field: "date", headerName: "Date", flex: 1 },
    { field: "change", headerName: "Change", flex: 1 },
  ]
  const rows: GridRowsProp = items.map((value, index) => {
    return {
      id: index,
      idOwn: value.id,
      idStorage: value.idStorage,
      date: value.date,
      change: value.change,
    }
  })
  return (
    <Box
      display={"flex"}
      minHeight={"15vh"}
      flexBasis={"auto"}
      flexGrow={1}
      flexShrink={0}
    >
      <DataGrid rows={rows} columns={cols} />
    </Box>
  )
})
export default ItemBalanceList
