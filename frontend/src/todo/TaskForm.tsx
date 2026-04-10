import type {Task} from "./TaskType.ts";

interface TaskFormProps {
    initialTask?: Task;
};

export const TaskForm = ({initialTask}: TaskFormProps) => {

    return (
        <>
        <h2>Task Form</h2>
        <form>
            <label htmlFor={'Title'}>Title</label>
            <input id={'Title'} type={'text'} />

            <label htmlFor={'Description'}>Description</label>
            <input id={'Description'} type={'text'} />

            <input type={'submit'} value={'Add Task'} onClick={'javascript:Alert("hello")'} />
        </form>
        </>
    );
}
