import React from 'react';
import type {Task} from "./TaskType.ts";

type TaskProps = {
    initialTask: Task
}

export const TaskItem = ({initialTask}: TaskProps) => {

    return (

        <li className="dark:p-2"
            aria-label={initialTask.title}
            id={initialTask.id}>
            {`${initialTask.title}`}
            {`${initialTask.description}`}
        </li>

    );
};

export default TaskItem;