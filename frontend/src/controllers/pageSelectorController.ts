export const pageSelectorController = {
  pageSelection: 0,
  mainPageSetState: (newVal: number) => {},
  update: (newValue: number) => {
    pageSelectorController.mainPageSetState(newValue);
  },
};
