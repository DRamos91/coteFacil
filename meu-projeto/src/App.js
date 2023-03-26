import React, { useState } from 'react';

function App() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <h1>Contador: {count}</h1>
      <button onClick={() => setCount(count + 1)}>Incrementar</button>
      <button onClick={() => setCount(0)}>Zerar</button>
    </div>
  );
}

export default App;

