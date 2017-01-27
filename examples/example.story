Title: Example story

D1_0:
    You are confronted with a serious question: To cheese it or not to cheese it?
    
    Responses:
        Cheese it! -> 50% to D2_0, 50% to D2_1
        Don't cheese it! -> 100% to D2_2

D2_0:
    Note: This is a comment. Its text will not be included in the XML output.
    You were able to cheese it!
    
    Responses:
        Proceed -> 100% to D3_0

D2_1:
    Comment: This is also a comment.
    You were unsuccessful at cheesing it!
    
    Responses:
        Proceed -> 100% to D3_0

D2_2:
    Comment: This node will attempt to break the parser's text handling features.
    You proceed not to cheese it.
    This is an additional line to test the parser.
    
    Yet another line!
    
    Responses:
        Proceed -> 100% to D3_0
        
D3_0:
    Regardless of whether you cheesed it, something happened.
    
    Responses:
        End -> 100% to END with (Examplereward; amazing description; 27)