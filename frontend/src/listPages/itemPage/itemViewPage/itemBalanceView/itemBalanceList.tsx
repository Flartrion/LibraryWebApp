import { memo, useMemo } from "react"
import ItemBalance from "../../../../dataclasses/itemBalance"
import { Box } from "@mui/material"
import { DataGrid, GridColDef, GridRowsProp } from "@mui/x-data-grid"

interface ItemBalanceGridProps {
  items: ItemBalance[]
}

const ItemBalanceList = memo(({ items }: ItemBalanceGridProps) => {
  const cols: GridColDef[] = useMemo(
    () => [
      { field: "idOwn", headerName: "Entry ID", flex: 1 },
      { field: "idStorage", headerName: "Storage ID", flex: 1 },
      { field: "date", headerName: "Date", flex: 1 },
      { field: "change", headerName: "Change", flex: 1 },
    ],
    [items]
  )
  const rows: GridRowsProp = useMemo(
    () =>
      items.map((value, index) => {
        return {
          id: index,
          idOwn: value.id,
          idStorage: value.idStorage,
          date: value.date,
          change: value.change,
        }
      }),
    [items]
  )
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
