/**
 * Is actually used for fields, too,
 * so as not to breed enums different only in a single member
 */
const enum ItemActionEnum {
  id,
  isbn,
  rlbc,
  title,
  authors,
  type,
  details,
  language,
  processing,
}

export default ItemActionEnum;
