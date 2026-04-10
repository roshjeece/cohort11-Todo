import {render, screen} from "@testing-library/react";
import {TaskForm} from "../TaskForm.tsx";
import {expect} from "vitest";
import {userEvent} from "@testing-library/user-event";

describe('Task Form', () => {
    const user = userEvent.setup();

    it('should display form heading', () => {
        render(<TaskForm />);

        expect(screen.getByRole('heading', {name: /task form/i})).toBeInTheDocument();
        expect(screen.getByLabelText( /Title/i)).toBeInTheDocument();
        expect(screen.getByRole('textbox', {name: /title/i})).toBeInTheDocument();
        expect(screen.getByRole('textbox', {name: /description/i})).toBeInTheDocument();
        expect(screen.getByRole('button', {name: /add task/i})).toBeInTheDocument();
    });

    it('should be able to input into fields and click submit', async () => {
        render(<TaskForm/>)
        const title = screen.getByRole('textbox', {name: /title/i});
        const description = screen.getByRole('textbox', {name: /description/i});
        const submit = screen.getByRole('button', {name: /add task/i});

        await user.type(title, 'new title');
        expect(title).toHaveValue('new title');

        await user.type(description, 'new description');
        expect(description).toHaveValue('new description');

        await user.click(submit);
        expect(submit).toHaveBeenCalledOnce();

    });
});