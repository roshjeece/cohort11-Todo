import type {Task} from "./TaskType.ts";
import {yupResolver} from "@hookform/resolvers/yup/src";
import {useForm} from "react-hook-form";
import * as yup from "yup"
import {axiosSaveTask} from "./TaskService.ts";

const { object, string, number } = yup;

const validationSchema = object({
    id: number(),
    title: string().required('bad title'),
    description: string().required('bad description'),
})

interface TaskFormProps {
    initialTask?: Task;
}

function TaskForm({initialTask}: TaskFormProps) {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<Task>({
        resolver: yupResolver(validationSchema)
    });


    function onSubmit(data: Task) {
        axiosSaveTask(data);
    }

    return (
        <div>
            <h2>Task Form</h2>
            <form onSubmit={handleSubmit(onSubmit)} method={'POST'}>
                <label htmlFor={'title'}>Title</label>
                <input id={'title'} type={'text'} {...register("title")}/>

                <label htmlFor={'description'}>Description</label>
                <input id={'description'} type={'text'} {...register("description")}/>


                <input type={'hidden'} value={1} {...register('category.id')}/>
                <input type={'hidden'} value={'active'} {...register('category.label')}/>

                <input type={'submit'} value={'Add Task'}/>
            </form>
        </div>
    );
}

export default TaskForm;