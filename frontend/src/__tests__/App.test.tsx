import {render, screen} from "@testing-library/react";
import App from "../App.tsx";
import {expect} from "vitest";
import userEvent from "@testing-library/user-event";

describe('App.tsx', () => {

    it('should display heading', () => {
        // Arrange
        render(<App />)
        // Assert
        expect(screen.getByRole('heading', {name: /started/i})).toBeInTheDocument();
        // role is literally the heading tags (<h1>, etc.)
        screen.logTestingPlaygroundURL();
    });

    it('should count button increment counter', async () => {
        // Arrange
        render(<App/>)
        const user = userEvent.setup();
// Assert
        const button = screen.getByRole('button', {name: /count/i});
        expect(screen.getByRole('button', {name:/0/i})).toBeVisible();

        // Act
        await user.click(button); // waits for the click AND all resulting state updates to settle
        // WAITING FOR COMPLETION OF ALL TASKS ASSOCIATED WITH THIS
        expect(screen.getByRole('button', {name: /1/i})).toBeVisible();
        // now the DOM reflects the update → test passes

    })

});