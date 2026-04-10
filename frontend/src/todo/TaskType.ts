export type Task = {
  id?: number | null;
  title: string;
  description: string;
  category: Category;
};

export type Category = {
  id: number;
  label: string;
}