Title: Story with circular reference

D1_0:
    This node has two choices. One will lead down a path that makes part of the node graph circular.
    
    Responses:
        A -> 100% to D2_0
        B -> 100% to D2_1

D2_0:
    This node ends properly.
    
    Responses:
        A -> 100% to END

D2_1:
    This node can either end or loop back to D1_0.
    While this story will eventually end, it will break the sanity checker and represents
    potentially poor practices in story development.
    
    Responses:
        A -> 50% to D1_0, 50% to END