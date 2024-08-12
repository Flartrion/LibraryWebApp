import { Box, Divider, Tab } from "@mui/material"
import Tabs from "@mui/material/Tabs"
import { useEffect, useState } from "react"
import pageSelectorModel from "./pageSelectorModel"
import PageEnum from "./pageEnum"
import pageSelectorController from "./pageSelectorController"
import userDataModel from "../support/userDataModel"

function SitePageSelector() {
  const [selection, setSelection] = useState(pageSelectorModel.pageSelection)
  function handleSelection(e: React.SyntheticEvent, newValue: number) {
    setSelection(newValue)
    pageSelectorController.updateModel(newValue)
  }
  useEffect(() => {
    pageSelectorController.subscribeView("pageSelector", setSelection)
    return () => {
      pageSelectorController.unsubscribeView("pageSelector")
    }
  })

  return (
    <Box
      sx={{
        flexGrow: 0,
        flexShrink: 0,
        flexBasis: "auto",
      }}
    >
      <Divider />
      <Tabs value={selection} onChange={handleSelection} variant="fullWidth">
        <Tab label="Items" value={PageEnum.Items} />
        <Tab label="Storages" value={PageEnum.Storages} />
        {(userDataModel.userRole ?? 999) <= 5 && (
          <Tab label="Users" value={PageEnum.Users} />
        )}
        {selection === PageEnum.Login && (
          <Tab label="Login" value={PageEnum.Login} />
        )}
      </Tabs>
      <Divider />
    </Box>
  )
}
export default SitePageSelector
