type Item = Id & {
  isbn: string;
  rlbc: string;
  authors: string;
  title: string;
  type: string;
  language: string;
  details: string;
};
export default Item;

export function newItem(
  id?: string,
  isbn?: string,
  rlbc?: string,
  authors?: string,
  title?: string,
  type?: string,
  language?: string,
  details?: string
): Item {
  const retval: Item = {} as Item;
  retval.id = id != undefined ? id : "";
  retval.isbn = isbn != undefined ? isbn : "";
  retval.rlbc = rlbc != undefined ? rlbc : "";
  retval.authors = authors != undefined ? authors : "";
  retval.title = title != undefined ? title : "";
  retval.type = type != undefined ? type : "";
  retval.language = language != undefined ? language : "";
  retval.details = details != undefined ? details : "";
  return retval;
}
