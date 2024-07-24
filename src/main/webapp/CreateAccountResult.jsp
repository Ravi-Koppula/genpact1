<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Account Result</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }
        .container {
            background-color: #ffffff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 400px;
        }
        .container h2 {
            margin-top: 0;
        }
        .message {
            font-size: 18px;
            margin-bottom: 20px;
        }
        .error {
            color: red;
        }
        .success {
            color: green;
        }
        .button {
            background: linear-gradient(to right, #1e90ff, #00bfff);
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background 0.3s ease;
            text-decoration: none;
        }
        .button:hover {
            background: linear-gradient(to right, #00bfff, #1e90ff);
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Account Creation Result</h2>
        <p class="message <%= (request.getAttribute("success") != null) ? "success" : "error" %>">
            <%= request.getAttribute("message") %>
        </p>
        <a href="createAccount.jsp" class="button">Create Another Account</a>
        <a href="adminPage.jsp" class="button">Back to Admin Page</a>
    </div>
</body>
</html>
