import {
  Alert,
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
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react"
import BackdropFallback from "../../../../support/fallbacks/backdropFallback"
import itemBalanceModel from "./itemBalanceModel"
import itemBalanceController from "./itemBalanceController"
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

    const handleNewCancel = useMemo(
      () => () => {
        setNewDialogOpen(false)
      },
      [setNewDialogOpen]
    )

    useEffect(() => {
      //   console.log("loaded!")
      itemBalanceController.viewSetStoragesLoadedState = setStoragesLoaded
      itemBalanceController.loadStorages()
      return () => {
        // console.log("unloaded!")
        itemBalanceController.viewSetStoragesLoadedState = undefined
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
            onChange={(event) => {
              console.log(event.target.value)
            }}
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
        <Box flex={"1 1 auto"}>
          <Alert severity="error">W.I.P.</Alert>
        </Box>
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
