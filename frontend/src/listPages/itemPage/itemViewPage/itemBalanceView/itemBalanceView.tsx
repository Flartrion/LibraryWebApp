import {
  Box,
  Button,
  MenuItem,
  Skeleton,
  TextField,
  Typography,
} from "@mui/material"
import {
  lazy,
  memo,
  Suspense,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react"
import BackdropFallback from "../../../../support/fallbacks/backdropFallback"
import itemBalanceModel from "./itemBalanceModel"
import itemBalanceController from "./itemBalanceController"
import DefaultFallback from "../../../../support/fallbacks/defaultFallback"
const ItemBalanceList = lazy(() => import("./itemBalanceList"))
const ItemBalanceNewDialog = lazy(() => import("./itemBalanceNewDialog"))

interface ItemBalanceProps {
  id: string
  toView: () => void
}

const ItemBalanceView = memo(
  ({ id, toView }: ItemBalanceProps) => {
    // function ItemBalanceView({ item, toView }: ItemBalanceProps) {
    const [newDialogOpen, setNewDialogOpen] = useState(false)
    const [storagesLoaded, setStoragesLoaded] = useState(false)
    const [entries, setEntries] = useState(
      itemBalanceModel.historyEntriesFiltered
    )
    const balanceChangeFirstOpen = useRef(false)

    function handleEditBalance(e: React.MouseEvent<HTMLButtonElement>) {
      balanceChangeFirstOpen.current = true
      setNewDialogOpen(true)
    }

    const handleNewCancel = useCallback(() => {
      setNewDialogOpen(false)
    }, [setNewDialogOpen])

    const handleChangeFilter = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      // console.log(e.target.value)
      itemBalanceController.filterByStorage(e.target.value)
    }

    useEffect(() => {
      //   console.log("loaded!")
      itemBalanceController.viewSetStoragesLoadedState = setStoragesLoaded
      itemBalanceController.dialogSetStoragesLoadedState = setStoragesLoaded
      itemBalanceController.setVisibleEntriesState = setEntries
      itemBalanceController.loadStorages()
      if (entries == undefined) {
        itemBalanceController.loadEntries(id)
      }
      return () => {
        // console.log("unloaded!")
        itemBalanceController.viewSetStoragesLoadedState = undefined
        itemBalanceController.dialogSetStoragesLoadedState = undefined
        itemBalanceController.setVisibleEntriesState = undefined
        itemBalanceController.unloadStorages()
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
          <Button
            sx={{ flex: 1 }}
            variant="contained"
            onClick={handleEditBalance}
          >
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
        {entries != null ? (
          <Suspense fallback={<DefaultFallback />}>
            <ItemBalanceList items={entries} />
          </Suspense>
        ) : (
          <DefaultFallback />
        )}
        {balanceChangeFirstOpen.current && (
          <Suspense fallback={<BackdropFallback />}>
            <ItemBalanceNewDialog
              id={id}
              onCancel={handleNewCancel}
              open={newDialogOpen}
            />
          </Suspense>
        )}
      </Box>
    )
  },
  (prevProps, nextProps) => {
    return prevProps.id == nextProps.id && prevProps.toView === nextProps.toView
  }
)
export default ItemBalanceView
