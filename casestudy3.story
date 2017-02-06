Title: Perspective of Principal Investigator

N1:
    You are the Principal Investigator of a lab that is researching the properties of interactions between cells and viruses. You are relatively newly hired as full faculty at your university, and you have multiple projects going on at once in attempt to establish yourself as a credible and important researcher. 
    The most important project you are working on—your first significant grant from the NSF as faculty— has been a bit of a fiasco because you need to finish and publish the paper soon if it has any chance of making the prestigious journal Molecular Cell, which would boost your reputation, solidify your place at the university, and likely enable you to fund more research. However, the post-doc you hired to manage the lab has not met your expectations; she has made several big mistakes and her progress has been unsatisfactorily slow. 
    Finally, only a week before the journal’s submission deadline, she has gathered enough data to establish an initial set that confirms your hypothesis. The results look significant enough to publish, but she still needs to collect supplementary data and consolidate and elaborate her analysis to form a coherent, well-written and publishable paper, tailored to the journal’s format. You have your first meeting with the managing post-doc after you have overviewed the data collected and established analysis. She seems unaware that you are running behind, and tells you that she simply can’t keep up with the demands of the project because her cat has been sick lately. You tell her: 
    
    Responses:
        Tell her that her excuse is horrible and her career and this project are more important than her cat. She should get the data in as soon as possible if she wants to continue working in your lab. -> 100% to N6
        Don’t reprimand her or threaten her, but offer her a raise and a permanent position if she gets the rest of the supplementary data and analysis in by the deadline. -> 100% to N2
        Since she looks exhausted and you are fed up with her ineptitude anyway, you tell her you will take up the project yourself and manage the accumulation of the supplementary data even though it will lead to great personal cost in your other projects. -> 100% to RESULT_H

N2:
    Only two days before the official deadline, she hands you a complete set of results and analysis. You are completely swamped with managing other projects and professorial work, and if you want it to make the journal, prioritizing the data analysis to convey the significance of this study will be more important than thoroughly checking the data. You
    
    Responses:
        Give the hard data a quick look-over and spend most of your time on precisely editing the analysis so that it looks as presentable as possible for the journal’s reviewers. -> 100% to N3
        Thoroughly check the data, sacrificing other demands. -> 100% to N7

N3:
    You have submitted to the journal and it is awaiting review. After a few weeks, you receive an email from a scientist at another university, who is reviewing your paper. He notes that he has discovered a mathematical impossibility in the supplementary data from the paper and has a few questions about the data’s source and viability. You glance at the data and see what he’s talking about: in the supplementary data that your post-doc procured after that tense meeting, there’s something that’s just not quite right.  You:
    
    Responses:
        Talk to the post-doc directly about the issue with the paper. -> 100% to N8
        Notify the head of your institution that you believe your employer had fabricated data. -> 100% to N4
        Do nothing. -> 100% to RESULT_G

N4:
    After a few days, you have been wracked with nerves. You haven’t been able to communicate with the institution directly, but upon looking at the supplementary data further you are pretty sure that it has been fabricated. If you lose this paper you lose your chance to publish in Molecular Cell and your first big grant goes to waste, which doesn’t bode well in the university’s eyes. Yet at this point, since you’ve already notified authorities, there’s no way to pretend that the data is accurate. You:
    
    Responses:
        Withdraw the supplementary data only in hopes that the journal will still consider the validity of the original results. -> 100% to N5
        Withdraw the entire paper. -> 100% to RESULT_A

N5:
    You receive information that the institution has notified NSF OIG about the errors in your paper. You don’t know how this will affect the paper’s publication so you talk to the head of your department. He seems dismayed that you weren’t able to catch the mistake and hints that the journal will likely reject your submission without supplementary data, and in knowledge that at least some part of the research process was compromised. You are nervous that this will have adverse impacts upon your own reputation, but you still don’t want to give up your hopes for publication. You:
    
    Responses:
        Call the post-doc into your office to try to get her to admit her mistake to the NSF. -> 100% to RESULT_F
        Withdraw the entire paper. -> 100% to RESULT_A

N6:
    After several days, the post-doc still has no definitive results. You remind her again that time is of the essence and finally, days before the submission deadline, she gets the data in. You are completely swamped with other work managing the other projects in your lab, and you know that a full, comprehensive review of the data would mean that you lose the possibility of finishing the paper in time for the submission deadline of Molecular Cell. You:
    
    Responses:
        Sacrifice the chance and submit it to another, less prestigious journal with a later deadline and less strict expectations. This gives you time to thoroughly review the paper. -> 100% to N7
        Give the data a quick scan, edit some key parts of the analysis and submit it to Molecular Cell, in hopes of salvaging your career. -> 100% to N3

N7:
    Note: This node may break the parser.
    While meticulously reviewing the paper, you notice that there is a mathematical impossibility in the supplementary data that you pressured the post-doc to obtain. These numbers could not have possibly come out of authentic experimental procedures. Suspicious that something has gone wrong or that the post-doc has done something wrong, you:
    
    Responses:
        Talk with the post-doc to try to figure out what’s going on. -> 100% to N8
        Report the mistake directly to the head of your institution; you are sick of dealing with her. -> 100% to RESULT_E
        Publish the paper anyway without confronting her or the institution; the supplementary data is not extremely important. -> 100% to RESULT_G

N8:
    You call her into your office and point out the mistake. You notice that right away she appears tense; hesitating to answer your questions. At first she claims that she doesn’t know what you’re talking about and that all the data has been experimentally verified as promised, but you explain to her that that is literally impossible. Finally, it becomes clear that, instead of repeating the experiment under different conditions as necessary to obtain the supplementary data, she used a simple mathematical formula to replicate the initial findings under different conditions. You can’t believe she would jeopardize the project so carelessly—now this paper is doomed and you both face potential disciplinary consequences. You: 
    
    Responses:
        Fire her from your lab on the spot. This kind of laziness is unacceptable in your lab. -> 100% to N9
        Notify the head of your institution. -> 100% to RESULT_E
        Reprimand her sharply but tell her you’ll work with her to try to salvage the paper; keep the data fabrication a secret. -> 100% to N10

N9:
    After she’s gone you have to decide what to do with the lab project. You have a presumably accurate set of initial data, a haphazardly put-together analysis, and fabricated supplementary data. But discarding the entire project would constitute a waste of funding, not to mention the lack of publication of results that still could be important. You:
    
    Responses:
        Publish a pre-print of the paper without supplementary data; send the paper into Molecular Cell for review, without the supplementary data, with your name on it instead of hers. -> 100% to RESULT_B
        Take the time to collect the supplementary data yourself; send the paper into a lesser-known journal with a later deadline. -> 100% to RESULT_C
        Scrap the project; do not publish the paper. You have no way of knowing whether the rest of the information was accurately collected. -> 100% to RESULT_D

RESULT_A:
    The NSF finds the post-doc guilty of fabricating data; she is fired and debarred for 2 years. Thankfully, though, because you recognized the mistake, you are not held culpable. You have the chance to re-do the supplementary data, edit the paper, and submit to a different journal with a later deadline.  
    
    Responses:
        End -> 100% to END with (Badge name; Some kind of description; 15)

RESULT_B:
    The paper is rejected at the journal because of lack of sufficient evidence to back up the analysis. The head of your institution is dismayed that your NSF grant went to waste. Furthermore, the post-doc you fired has been spreading rumors about you, saying that you threatened her and failed to give her clear expectations for managing the lab. You still have the chance to publish the paper in a less prestigious journal, but you’ve been so stressed and overwhelmed by this fiasco that your other projects are suffering. It will be a challenge to get back on track to prove yourself early in your career, as you had hoped. 
    
    Responses:
        End -> 100% to END with (RESULT_B; You were too quick to publish; 10)

RESULT_C:
    The paper is published in the less prestigious journal. When the university asks about why you fired the post-doc and failed to submit the data to Molecular Cell as planned, you are forced to explain the situation. They are relieved to have avoided a near-scandal, but explain that you should have reported the researcher. The head of your department seems wary and suspicious of you. 
    
    Responses:
        End -> 100% to END with (RESULT_C; You have reached an uneasy comprompise; 15)

RESULT_D:
    The head of your department at the university is displeased with the failure of your lab to see this project through especially after you had such high hopes for it, and mystified as to the impulse firing of this post-doc. Your lab loses its start-up funds from the university. You know your research is worthy of more consideration and you’re upset that it could not have gone better. 
    
    Responses:
        End -> 100% to END with (Another badge name; These statements allow users to get rewards in points for completing specific objectives; 5)

RESULT_E:
    The head of your institution notifies the NSF, who launch an investigation against the post-doc. They find her guilty of research misconduct and impose 2 years of debarment; she loses her position at the university. Meanwhile, you are forced to re-do the supplementary data and submit the paper to a less prestigious journal, where it is published. The university still considers the accomplishment significant under the circumstances, though, and the head of your department seems pleased with your work and the way you handled the situation.
    
    Responses:
        End -> 100% to END with (RESULT_E; You attempted to make the best of things; 20)

RESULT_F:
    You call her into your office and point out the mistake. You notice that right away she appears tense; hesitating to answer your questions. At first she claims that she doesn’t know what you’re talking about and that all the data has been experimentally verified as promised, but you explain to her that that is literally impossible. Finally, it becomes clear that, instead of repeating the experiment under different conditions as necessary to obtain the supplementary data, she used a simple mathematical formula to replicate the initial findings under different conditions. She is so apologetic you almost take pity on her, but at this point it’s too late to repair any damage. The NSF finds her guilt of research misconduct and imposes 2 years debarment; your university is dismayed at the way you handled the situation. 
    
    Responses:
        End -> 100% to END with (RESULT_F; Description; 15)

RESULT_G:
    After a few days, you receive an email from the NSF. Apparently, the reviewer has reported your lab directly to the NSF because he believes your paper fabricated data. Both you and the post-doc have to testify to what happens; she eventually admits to fabricating data and you are found culpable in the investigation, especially since you didn’t respond to the reviewer’s email. The paper is forcibly withdrawn and both you and the post-doc are put under 2 years debarment and fired from the university. Your prospects of getting a similar position elsewhere look unlikely. 
    
    Responses:
        End -> 100% to END with (RESULT_G; Worst case scenario; 0)

RESULT_H:
    Because you spent so much time collecting data, you run out of time to refine the analysis and market your research well to the journal. The paper is rejected at the journal; your NSF grant has been wasted and you must start a new project, this time working in conjunction with those you have hired.
    
    Responses:
        End -> 100% to END with (Impatience; Your impatience has not paid off; 10)



