import {
  Box,
  Button,
  MenuItem,
  Skeleton,
  TextField,
  Typography,
} from "@mui/material"
import { lazy, memo, Suspense, useEffect, useRef, useState } from "react"
import BackdropFallback from "../../../../support/fallbacks/backdropFallback"
import itemBalanceModel from "./itemBalanceModel"
import itemBalanceController from "./itemBalanceController"
import DefaultFallback from "../../../../support/fallbacks/defaultFallback"
import ItemBalanceDeleteDialog from "./itemBalanceDeleteDialog"
const ItemBalanceList = lazy(() => import("./itemBalanceList"))
const ItemBalanceNewDialog = lazy(() => import("./itemBalanceNewDialog"))

interface ItemBalanceProps {
  id: string
  toView: () => void
}

const ItemBalanceView = ({ id, toView }: ItemBalanceProps) => {
  const [newDialogOpen, setNewDialogOpen] = useState(false)
  const newDialogFirstOpen = useRef(false)
  const [deleteDialogOpen, setDeleteDialogOpen] = useState(false)
  const deleteDialogFirstOpen = useRef(false)
  const deleteId = useRef("")
  const [storagesLoaded, setStoragesLoaded] = useState(false)
  const [entries, setEntries] = useState(
    itemBalanceModel.historyEntriesFiltered
  )

  const handleNewBalance = (e: React.MouseEvent<HTMLButtonElement>) => {
    newDialogFirstOpen.current = true
    setNewDialogOpen(true)
  }

  const handleNewCancel = () => {
    setNewDialogOpen(false)
  }

  const handleDeleteBalance =
    (id: string) => (e: React.MouseEvent<HTMLButtonElement>) => {
      deleteDialogFirstOpen.current = true
      deleteId.current = id
      setDeleteDialogOpen(true)
    }

  const handleDeleteCancel = () => {
    setDeleteDialogOpen(false)
  }

  const handleChangeFilter = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    // console.log(e.target.value)
    itemBalanceController.filterByStorage(e.target.value)
  }

  useEffect(() => {
    // console.log("loaded!")
    itemBalanceController.viewSetStoragesLoadedState = setStoragesLoaded
    itemBalanceController.setVisibleEntriesState = setEntries
    itemBalanceController.loadStorages()
    if (entries == undefined) {
      itemBalanceController.loadEntries(id)
    }
    return () => {
      // console.log("unloaded!")
      itemBalanceController.viewSetStoragesLoadedState = undefined
      itemBalanceController.setVisibleEntriesState = undefined
      // itemBalanceController.unloadStorages()
    }
  }, [id])

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        flex: "1 1 auto",
        gap: "10px",
      }}
    >
      <Box
        sx={{
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-between",
          gap: "40px",
        }}
      >
        <Button sx={{ flex: 1 }} variant="contained" onClick={handleNewBalance}>
          Edit Balance
        </Button>
        <Button sx={{ flex: 1 }} variant="outlined" onClick={() => toView()}>
          Return
        </Button>
      </Box>
      {storagesLoaded ? (
        <TextField
          select
          label={"Filter"}
          InputLabelProps={{
            shrink: true,
          }}
          SelectProps={{
            displayEmpty: true,
          }}
          onChange={handleChangeFilter}
          defaultValue={""}
        >
          <MenuItem key={0} value={""}>
            <Typography>...</Typography>
          </MenuItem>
          {itemBalanceModel.storages.map((value, index) => (
            <MenuItem key={value.id} value={value.id}>
              <Typography>
                {index}: {value.address}
              </Typography>
            </MenuItem>
          ))}
        </TextField>
      ) : (
        <Skeleton variant="rounded" height="50px" width="100%"></Skeleton>
      )}
      {entries != undefined ? (
        <Suspense fallback={<DefaultFallback />}>
          <ItemBalanceList items={entries} handleDelete={handleDeleteBalance} />
        </Suspense>
      ) : (
        <DefaultFallback />
      )}
      {newDialogFirstOpen.current && (
        <Suspense fallback={<BackdropFallback />}>
          <ItemBalanceNewDialog
            id={id}
            onCancel={handleNewCancel}
            open={newDialogOpen}
          />
        </Suspense>
      )}
      {deleteDialogFirstOpen.current && (
        <Suspense fallback={<BackdropFallback />}>
          <ItemBalanceDeleteDialog
            id={deleteId.current}
            onCancel={handleDeleteCancel}
            open={deleteDialogOpen}
          />
        </Suspense>
      )}
    </Box>
  )
}

export default ItemBalanceView
