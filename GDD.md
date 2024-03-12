# DragUNO

## User Experience
>- Entirely using HTML elements and CSS.
>- Audio and image elements preloaded to reduce server strain.

## Frontend
>- Clients send all data with their "id" URL-encoded
>- Minimal data posted. Only index of their card within their deck and all user input.
>- Receive all data from the event stream clients are subscribed to. Do not rely on data sent back from post/get requests (data will be sent to all regardless).

## Backend
>- Store singular game session and state.
>- Create a dictionary of all clients containing their "id" and username.
>- All logic is done back here, with objects storing each player's deck.