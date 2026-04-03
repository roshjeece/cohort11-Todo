import type {Task} from "./TaskType.ts";
import axios, { type AxiosResponse } from "axios";

type GetTasks = () => Promise<Task[]>;
type AxiosGetTasks = () => Promise<Task[]>;

export const getAllTasks: GetTasks = async () => {
    const Response = await fetch('/api/v1/task/');
    return await Response.json();

}

export const axiosGetAllTasks: AxiosGetTasks = async () =>
    axios.get<Task[]>('api/v1/task/').then((r: AxiosResponse<Task[]>) => r.data)