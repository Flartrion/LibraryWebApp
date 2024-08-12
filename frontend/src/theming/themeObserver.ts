const themeObserver = {
  currentTheme: 0,
  updateView: (newTheme: number) => {},
  updateModel: (newTheme: number) => {
    themeObserver.currentTheme = newTheme
    themeObserver.updateView(newTheme)
  },
}
export default themeObserver
